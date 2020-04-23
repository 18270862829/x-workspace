package com.afiona.center.web.promotion.domain.service.aggregate.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.afiona.center.client.promotion.constants.choice.ContentTypeChoiceEnum;
import com.afiona.center.client.promotion.domain.aggregate.AddMoneyToBuyAggregate;
import com.afiona.center.client.promotion.domain.aggregate.FullReductionAggregate;
import com.afiona.center.client.promotion.domain.model.promotionrule.fullreduction.FullReductionCoupon;
import com.afiona.center.client.promotion.domain.model.promotionrule.fullreduction.FullReductionGitGoods;
import com.afiona.center.client.promotion.domain.model.promotionrule.fullreduction.FullReductionRule;
import com.afiona.center.client.promotion.domain.model.promotionrule.fullreduction.FullReductionStairRule;
import com.afiona.center.client.promotion.model.PromotionStairRuleKey;
import com.afiona.center.web.promotion.domain.service.aggregate.IAggregate;
import com.afiona.center.web.promotion.domain.service.aggregate.IBaseAggregate;
import com.afiona.center.web.promotion.infrastructure.dao.FullReductionCouponDAO;
import com.afiona.center.web.promotion.infrastructure.dao.FullReductionGitGoodsDAO;
import com.afiona.center.web.promotion.infrastructure.dao.FullReductionRuleDAO;
import com.afiona.center.web.promotion.infrastructure.dao.FullReductionStairRuleDAO;
import com.afiona.center.web.promotion.infrastructure.model.FullReductionCouponDO;
import com.afiona.center.web.promotion.infrastructure.model.FullReductionGitGoodsDO;
import com.afiona.center.web.promotion.infrastructure.model.FullReductionRuleDO;
import com.afiona.center.web.promotion.infrastructure.model.FullReductionStairRuleDO;
import com.afiona.center.client.promotion.util.JsonUtils;
import com.afiona.common.util.CloneUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.afiona.center.client.promotion.util.GenericHelper.*;

/**
 * 满减促销活动聚合器
 *
 * @author dengweiyi
 * @date 2020-02-12
 */
@Service
public class FullReductionAggregateService  implements IAggregate<FullReductionAggregate> {
    @Resource
    private IBaseAggregate<FullReductionAggregate> baseAggregateService;

    @Resource
    private FullReductionRuleDAO fullReductionRuleDAO;

    @Resource
    private FullReductionStairRuleDAO fullReductionStairRuleDAO;

    @Resource
    private FullReductionGitGoodsDAO fullReductionGitGoodsDAO;

    @Resource
    private FullReductionCouponDAO fullReductionCouponDAO;

    @Override
    public void aggregate(List<FullReductionAggregate> promotions){
        if(com.deepexi.util.CollectionUtil.isEmpty(promotions)){
            return;
        }
        baseAggregateService.aggregate(promotions);
        aggregateRules(promotions);
    }

    private void aggregateRules(List<FullReductionAggregate> promotions){
        List<FullReductionRuleDO> ruleDOList = getDOList(promotions, fullReductionRuleDAO);
        List<FullReductionRule> rules = CloneUtil.cloneList(ruleDOList, FullReductionRule.class);
        Map<Long, FullReductionRule> ruleMap = rules.stream().collect(Collectors.toMap(FullReductionRule::getPromotionId, Function.identity()));
        for(FullReductionAggregate fullReductionAggregate : promotions){
            FullReductionRule rule = ruleMap.get(fullReductionAggregate.getId());
            if(rule == null){
                continue;
            }
            fullReductionAggregate.setFullReductionRule(rule);
        }
        aggregateStairRules(promotions, rules);
    }

    private void aggregateStairRules(List<FullReductionAggregate> promotions, List<FullReductionRule> rules){
        List<FullReductionStairRuleDO> stairRuleDOList = getDOList(promotions, fullReductionStairRuleDAO);
        List<FullReductionStairRule> stairRules = CloneUtil.cloneList(stairRuleDOList, FullReductionStairRule.class);
        Map<Long, List<FullReductionStairRule>> stairRuleGroupMap = stairRules.stream().collect(Collectors.groupingBy(FullReductionStairRule::getPromotionId));
        for(FullReductionRule rule : rules){
            List<FullReductionStairRule> promotionStairRules = stairRuleGroupMap.get(rule.getPromotionId());
            if(CollectionUtil.isEmpty(promotionStairRules)){
                continue;
            }
            rule.setFullReductionStairRules(promotionStairRules);
        }

        Map<Long, FullReductionStairRuleDO> stairRuleMap = stairRuleDOList.stream()
                .collect(Collectors.toMap(FullReductionStairRuleDO::getId, Function.identity()));
        for(FullReductionStairRule stairRule : stairRules){
            FullReductionStairRuleDO stairRuleDO = stairRuleMap.get(stairRule.getId());
            if(stairRuleDO == null){
                continue;
            }
            stairRule.setPreferentialThreshold(toThreshold(stairRuleDO));
            stairRule.setPreferentialContent(toContent(stairRuleDO));
        }

        aggregateGoods(promotions, stairRules);
        aggregateCoupon(promotions, stairRules);
    }

    private void aggregateGoods(List<FullReductionAggregate> promotions, List<FullReductionStairRule> stairRules){
        List<FullReductionGitGoodsDO> goodsDOList = getDOList(promotions, fullReductionGitGoodsDAO);
        if(goodsDOList==null||goodsDOList.isEmpty()){
            return;
        }
        List<FullReductionGitGoods> goodsList = CloneUtil.cloneList(goodsDOList, FullReductionGitGoods.class);
        Map<PromotionStairRuleKey, List<FullReductionGitGoods>> goodsMap = goodsList.stream()
                .collect(Collectors.groupingBy(FullReductionGitGoods::getKey));
        for(FullReductionStairRule stairRule : stairRules){
            List<FullReductionGitGoods> stairRuleGoodsList = goodsMap.get(stairRule.getKey());
            if(CollectionUtil.isEmpty(stairRuleGoodsList)){
                continue;
            }
            stairRule.getPreferentialContent().getGift().setFullReductionGitGoods(stairRuleGoodsList);
        }
    }

    private void aggregateCoupon(List<FullReductionAggregate> promotions, List<FullReductionStairRule> stairRules){
        List<FullReductionCouponDO> couponDOList = getDOList(promotions, fullReductionCouponDAO);
        if(couponDOList==null||couponDOList.isEmpty()){
            return;
        }
        List<FullReductionCoupon> coupons = CloneUtil.cloneList(couponDOList, FullReductionCoupon.class);
        Map<PromotionStairRuleKey, List<FullReductionCoupon>> couponMap = coupons.stream()
                .collect(Collectors.groupingBy(FullReductionCoupon::getKey));
        for(FullReductionStairRule stairRule : stairRules){
            List<FullReductionCoupon> stairRuleCoupons = couponMap.get(stairRule.getKey());
            if(CollectionUtil.isEmpty(stairRuleCoupons)){
                continue;
            }
            stairRule.getPreferentialContent().getGift().setFullReductionCoupons(stairRuleCoupons);
        }
    }

    private FullReductionStairRule.PreferentialThreshold toThreshold(FullReductionStairRuleDO stairRuleDO){
        FullReductionStairRule.PreferentialThreshold threshold = new FullReductionStairRule.PreferentialThreshold();
        threshold.setThresholdChoice(stairRuleDO.getThresholdChoice());
        threshold.setFullAmount(stairRuleDO.getFullAmount());
        threshold.setFullPriceChoice(stairRuleDO.getFullPriceChoice());
        threshold.setFullNum(stairRuleDO.getFullNum());
        threshold.setFullNumChoice(stairRuleDO.getFullNumChoice());
        return threshold;
    }

    private FullReductionStairRule.PreferentialContent toContent(FullReductionStairRuleDO stairRuleDO){
        FullReductionStairRule.PreferentialContent content = new FullReductionStairRule.PreferentialContent();
        content.setContentTypeChoices(JsonUtils.jsonToList(stairRuleDO.getContentTypeChoice(), ContentTypeChoiceEnum.class));
        content.setAmount(toAmount(stairRuleDO));
        content.setGift(toGift(stairRuleDO));
        return content;
    }

    private FullReductionStairRule.Amount toAmount(FullReductionStairRuleDO stairRuleDO){
        FullReductionStairRule.Amount amount = new FullReductionStairRule.Amount();
        amount.setAmountChoice(stairRuleDO.getAmountChoice());
        amount.setReduceAmount(stairRuleDO.getReduceAmount());
        amount.setDiscount(stairRuleDO.getDiscount());
        amount.setLimit(stairRuleDO.getLimits());
        return amount;
    }

    private FullReductionStairRule.Gift toGift(FullReductionStairRuleDO stairRuleDO){
        FullReductionStairRule.Gift gift = new FullReductionStairRule.Gift();
        gift.setGiftChoice(stairRuleDO.getGiftChoice());
        gift.setScore(stairRuleDO.getScore());
        return gift;
    }
}
