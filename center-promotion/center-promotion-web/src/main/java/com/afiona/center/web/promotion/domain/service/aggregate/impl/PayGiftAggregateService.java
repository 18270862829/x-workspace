package com.afiona.center.web.promotion.domain.service.aggregate.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.afiona.center.client.promotion.constants.choice.GiftChoiceEnum;
import com.afiona.center.client.promotion.domain.aggregate.AddMoneyToBuyAggregate;
import com.afiona.center.client.promotion.domain.aggregate.PayGiftAggregate;
import com.afiona.center.client.promotion.domain.model.promotionrule.paygift.PayGiftCoupon;
import com.afiona.center.client.promotion.domain.model.promotionrule.paygift.PayGiftGoods;
import com.afiona.center.client.promotion.domain.model.promotionrule.paygift.PayGiftRule;
import com.afiona.center.client.promotion.domain.model.promotionrule.paygift.PayGiftStairRule;
import com.afiona.center.web.promotion.domain.service.aggregate.IAggregate;
import com.afiona.center.web.promotion.domain.service.aggregate.IBaseAggregate;
import com.afiona.center.web.promotion.infrastructure.dao.PayGiftCouponDAO;
import com.afiona.center.web.promotion.infrastructure.dao.PayGiftGoodsDAO;
import com.afiona.center.web.promotion.infrastructure.dao.PayGiftRuleDAO;
import com.afiona.center.web.promotion.infrastructure.dao.PayGiftStairRuleDAO;
import com.afiona.center.web.promotion.infrastructure.model.*;
import com.afiona.center.client.promotion.util.JsonUtils;
import com.afiona.common.util.CloneUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.afiona.center.client.promotion.util.GenericHelper.getDOList;

/**
 * 支付有礼聚合器
 *
 * @author LiJinXing
 * @date 2020/2/25
 */
@Service
public class PayGiftAggregateService  implements IAggregate<PayGiftAggregate> {

    @Resource
    private IBaseAggregate<PayGiftAggregate> baseAggregateService;

    @Resource
    private PayGiftRuleDAO payGiftRuleDAO;

    @Resource
    private PayGiftStairRuleDAO payGiftStairRuleDAO;

    @Resource
    private PayGiftGoodsDAO payGiftGoodsDAO;

    @Resource
    private PayGiftCouponDAO payGiftCouponDAO;

    @Override
    public void aggregate(List<PayGiftAggregate> payGiftAggregates) {
        if(com.deepexi.util.CollectionUtil.isEmpty(payGiftAggregates)){
            return;
        }
       baseAggregateService.aggregate(payGiftAggregates);
       aggregatePayGiftRule(payGiftAggregates);
    }

    private void aggregatePayGiftRule(List<PayGiftAggregate> payGiftAggregates) {
        List<PayGiftRuleDO> ruleDOList = getDOList(payGiftAggregates,payGiftRuleDAO);
        List<PayGiftRule> rules = CloneUtil.cloneList(ruleDOList, PayGiftRule.class);
        Map<Long, PayGiftRule> ruleMap = rules.stream().collect(Collectors.toMap(PayGiftRule::getPromotionId, Function.identity()));
        for (PayGiftAggregate payGiftAggregate : payGiftAggregates) {
            PayGiftRule payGiftRule = ruleMap.get(payGiftAggregate.getId());
            if(payGiftRule==null){
                continue;
            }
            payGiftAggregate.setPayGiftRule(payGiftRule);
        }

        aggregateStairRules(payGiftAggregates,rules);
    }

    private void aggregateStairRules(List<PayGiftAggregate> payGiftAggregates, List<PayGiftRule> rules) {
        List<PayGiftStairRuleDO> stairRuleDOList = getDOList(payGiftAggregates, payGiftStairRuleDAO);
        List<PayGiftStairRule> stairRules = CloneUtil.cloneList(stairRuleDOList, PayGiftStairRule.class);
        int index=-1;
        for (PayGiftStairRule stairRule : stairRules) {
            index++;
            stairRule.setGiftChoice(JsonUtils.jsonToList(stairRuleDOList.get(index).getGiftChoice(), GiftChoiceEnum.class));
        }
        Map<Long, List<PayGiftStairRule>> stairRuleGroupMap = stairRules.stream().collect(Collectors.groupingBy(PayGiftStairRule::getPromotionId));
        for (PayGiftRule rule : rules) {
            List<PayGiftStairRule> payGiftStairRules = stairRuleGroupMap.get(rule.getPromotionId());
            if(CollectionUtil.isEmpty(payGiftAggregates)){
                continue;
            }
            rule.setPayGiftStairRules(payGiftStairRules);
        }

        Map<Long, PayGiftStairRuleDO> stairRuleMap = stairRuleDOList.stream()
                .collect(Collectors.toMap(PayGiftStairRuleDO::getId, Function.identity()));
        for (PayGiftStairRule stairRule : stairRules) {
            PayGiftStairRuleDO payGiftStairRuleDO = stairRuleMap.get(stairRule.getId());
            if(payGiftStairRuleDO==null){
                continue;
            }
            stairRule.setPreferentialThreshold(toPreferentialThreshold(payGiftStairRuleDO));
            stairRule.setPayGiftGoods(aggregateGiftGoods(stairRule));
            stairRule.setPayGiftCoupons(aggregateGiftCoupon(stairRule));
        }
    }

    private PayGiftStairRule.PreferentialThreshold toPreferentialThreshold(PayGiftStairRuleDO payGiftStairRuleDO) {
        return CloneUtil.clone(payGiftStairRuleDO,PayGiftStairRule.PreferentialThreshold.class);
    }

    private List<PayGiftGoods> aggregateGiftGoods(PayGiftStairRule payGiftStairRule) {
        List<PayGiftGoodsDO> giftGoodsDOList = payGiftGoodsDAO.list(new LambdaQueryWrapper<PayGiftGoodsDO>().eq(PayGiftGoodsDO::getStairRuleId, payGiftStairRule.getId()));
        return CloneUtil.cloneList(giftGoodsDOList, PayGiftGoods.class);
    }

    private List<PayGiftCoupon> aggregateGiftCoupon(PayGiftStairRule payGiftStairRule) {
        List<PayGiftCouponDO> giftCouponDOList = payGiftCouponDAO.list(new LambdaQueryWrapper<PayGiftCouponDO>().eq(PayGiftCouponDO::getStairRuleId, payGiftStairRule.getId()));
        return CloneUtil.cloneList(giftCouponDOList,PayGiftCoupon.class);
    }

}
