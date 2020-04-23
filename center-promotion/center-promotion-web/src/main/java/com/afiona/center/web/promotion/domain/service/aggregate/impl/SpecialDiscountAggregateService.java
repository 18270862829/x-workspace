package com.afiona.center.web.promotion.domain.service.aggregate.impl;

import com.afiona.center.client.promotion.domain.aggregate.AddMoneyToBuyAggregate;
import com.afiona.center.client.promotion.domain.aggregate.SpecialDiscountAggregate;
import com.afiona.center.client.promotion.domain.model.promotionrule.specialdiscount.SpecialDiscountGoods;
import com.afiona.center.client.promotion.domain.model.promotionrule.specialdiscount.SpecialDiscountRule;
import com.afiona.center.web.promotion.domain.service.aggregate.IAggregate;
import com.afiona.center.web.promotion.domain.service.aggregate.IBaseAggregate;
import com.afiona.center.web.promotion.infrastructure.dao.SpecialDiscountGoodsDAO;
import com.afiona.center.web.promotion.infrastructure.dao.SpecialDiscountRuleDAO;
import com.afiona.center.web.promotion.infrastructure.model.SpecialDiscountGoodsDO;
import com.afiona.center.web.promotion.infrastructure.model.SpecialDiscountRuleDO;
import com.afiona.common.util.CloneUtil;
import com.deepexi.util.CollectionUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.afiona.center.client.promotion.util.GenericHelper.getDOList;

/**
 * 限时折扣聚合器
 *
 * @author LiJinXing
 * @date 2020/2/18
 */
@Service
public class SpecialDiscountAggregateService  implements IAggregate<SpecialDiscountAggregate> {

    @Resource
    private IBaseAggregate<SpecialDiscountAggregate> baseAggregateService;

    @Resource
    private SpecialDiscountRuleDAO specialDiscountRuleDAO;

    @Resource
    private SpecialDiscountGoodsDAO specialDiscountGoodsDAO;

    @Override
    public void aggregate(List<SpecialDiscountAggregate> promotions){
        if(CollectionUtil.isEmpty(promotions)){
            return;
        }
        baseAggregateService.aggregate(promotions);
        aggregateSpecialDiscountRule(promotions);
    }

    private void aggregateSpecialDiscountRule(List<SpecialDiscountAggregate> promotions) {
        List<SpecialDiscountRuleDO> ruleDOList = getDOList(promotions, specialDiscountRuleDAO);
        List<SpecialDiscountRule> rules = CloneUtil.cloneList(ruleDOList, SpecialDiscountRule.class);
        Map<Long, SpecialDiscountRule> ruleMap = rules.stream().collect(Collectors.toMap(SpecialDiscountRule::getPromotionId, Function.identity()));
        for (SpecialDiscountAggregate specialDiscountAggregate : promotions) {
            SpecialDiscountRule specialDiscountRule = ruleMap.get(specialDiscountAggregate.getId());
            if(specialDiscountRule==null){
                return;
            }
            specialDiscountAggregate.setSpecialDiscountRule(specialDiscountRule);
        }

        Map<Long, SpecialDiscountRuleDO> map = ruleDOList.stream().collect(Collectors.toMap(SpecialDiscountRuleDO::getId, Function.identity()));
        for (SpecialDiscountRule specialDiscountRule : rules) {
            SpecialDiscountRuleDO specialDiscountRuleDO = map.get(specialDiscountRule.getId());
            if(specialDiscountRuleDO==null){
                continue;
            }
            specialDiscountRule.setGlobalDiscountSetting(toGlobalDiscountSetting(specialDiscountRuleDO));
        }
        aggregateSpecialDiscountGoods(promotions,rules);
    }

    private SpecialDiscountRule.GlobalDiscountSetting toGlobalDiscountSetting(SpecialDiscountRuleDO specialDiscountRuleDO) {
        SpecialDiscountRule.GlobalDiscountSetting globalDiscountSetting =new SpecialDiscountRule.GlobalDiscountSetting();
        globalDiscountSetting.setAmount(specialDiscountRuleDO.getAmount());
        globalDiscountSetting.setChoice(specialDiscountRuleDO.getChoice());
        globalDiscountSetting.setDiscount(specialDiscountRuleDO.getDiscount());
        return globalDiscountSetting;
    }

    private void aggregateSpecialDiscountGoods(List<SpecialDiscountAggregate> promotions, List<SpecialDiscountRule> rules) {
        List<SpecialDiscountGoodsDO> specialDiscountGoodsDOS = getDOList(promotions, specialDiscountGoodsDAO);
        List<SpecialDiscountGoods> specialDiscountGoodsList = CloneUtil.cloneList(specialDiscountGoodsDOS, SpecialDiscountGoods.class);
        Map<Long, List<SpecialDiscountGoods>> listMap = specialDiscountGoodsList.stream().collect(Collectors.groupingBy(SpecialDiscountGoods::getPromotionId));
        for (SpecialDiscountRule rule : rules) {
            List<SpecialDiscountGoods> specialDiscountGoods = listMap.get(rule.getPromotionId());
            if(CollectionUtil.isEmpty(specialDiscountGoods)){
                continue;
            }
            rule.setSpecialDiscountGoods(specialDiscountGoods);
        }
    }


}
