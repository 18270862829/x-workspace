package com.afiona.center.web.promotion.domain.service.aggregate.impl;

import com.afiona.center.client.promotion.domain.aggregate.SuitGroupAggregate;
import com.afiona.center.client.promotion.domain.model.promotionrule.suit.SuitFixedSuitGoods;
import com.afiona.center.client.promotion.domain.model.promotionrule.suit.SuitFixedSuitRule;
import com.afiona.center.client.promotion.domain.model.promotionrule.suit.SuitGroupRule;
import com.afiona.center.web.promotion.domain.service.aggregate.IAggregate;
import com.afiona.center.web.promotion.domain.service.aggregate.IBaseAggregate;
import com.afiona.center.web.promotion.infrastructure.dao.SuitFixedSuitGoodsDAO;
import com.afiona.center.web.promotion.infrastructure.dao.SuitFixedSuitRuleDAO;
import com.afiona.center.web.promotion.infrastructure.dao.SuitGroupRuleDAO;
import com.afiona.center.web.promotion.infrastructure.model.*;
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
 * 组合套装聚合器
 *
 * @author LiJinXing
 * @date 2020/2/19
 */
@Service
public class SuitGroupAggregateService  implements IAggregate<SuitGroupAggregate> {

    @Resource
    private IBaseAggregate<SuitGroupAggregate> baseAggregateService;

    @Resource
    private SuitGroupRuleDAO suitGroupRuleDAO;

    @Resource
    private SuitFixedSuitGoodsDAO suitFixedSuitGoodsDAO;

    @Resource
    private SuitFixedSuitRuleDAO suitFixedSuitRuleDAO;

    @Override
    public void aggregate(List<SuitGroupAggregate> promotions) {
        if(CollectionUtil.isEmpty(promotions)){
            return;
        }
        baseAggregateService.aggregate(promotions);
        aggregateSuitGroupRule(promotions);
    }

    private void aggregateSuitGroupRule(List<SuitGroupAggregate> promotions) {
        List<SuitGroupRuleDO> suitGroupRuleDOS = getDOList(promotions, suitGroupRuleDAO);
        List<SuitGroupRule> rules = CloneUtil.cloneList(suitGroupRuleDOS, SuitGroupRule.class);
        Map<Long, SuitGroupRule> ruleMap = rules.stream().collect(Collectors.toMap(SuitGroupRule::getPromotionId, Function.identity()));
        for (SuitGroupAggregate suitGroupAggregate : promotions) {
            SuitGroupRule suitGroupRule = ruleMap.get(suitGroupAggregate.getId());
            if(suitGroupRule==null){
                return;
            }
            suitGroupAggregate.setSuitGroupRule(suitGroupRule);
        }
        aggregateSuitFixedSuitRule(promotions,rules);

    }

    private void aggregateSuitFixedSuitRule(List<SuitGroupAggregate> promotions, List<SuitGroupRule> rules) {
        List<SuitFixedSuitRuleDO> doList = getDOList(promotions, suitFixedSuitRuleDAO);
        List<SuitFixedSuitRule> suitFixedSuitRules = CloneUtil.cloneList(doList, SuitFixedSuitRule.class);
        Map<Long, List<SuitFixedSuitRule>> listMap = suitFixedSuitRules.stream().collect(Collectors.groupingBy(SuitFixedSuitRule::getPromotionId));
        for (SuitGroupRule groupRule : rules) {
            List<SuitFixedSuitRule> fixedSuitRules = listMap.get(groupRule.getPromotionId());
            if(CollectionUtil.isEmpty(fixedSuitRules)){
                continue;
            }
            groupRule.setSuitFixedSuitRules(fixedSuitRules);
        }
        aggregateSuitFixedSuitGoods(promotions, suitFixedSuitRules);
    }


    private void aggregateSuitFixedSuitGoods(List<SuitGroupAggregate> promotions, List<SuitFixedSuitRule> rules) {
        List<SuitFixedSuitGoodsDO> stairRuleDOList = getDOList(promotions, suitFixedSuitGoodsDAO);
        if(CollectionUtil.isEmpty(stairRuleDOList)){
            return;
        }
        List<SuitFixedSuitGoods> stairRules = CloneUtil.cloneList(stairRuleDOList, SuitFixedSuitGoods.class);
        Map<Long, List<SuitFixedSuitGoods>> suitFixedSuitGoodsMap = stairRules.stream().collect(Collectors.groupingBy(SuitFixedSuitGoods::getSuitFixedSuitId));
        for (SuitFixedSuitRule suitFixedSuitRule : rules) {
            List<SuitFixedSuitGoods> suitFixedSuitGoods = suitFixedSuitGoodsMap.get(suitFixedSuitRule.getId());
            if(CollectionUtil.isEmpty(suitFixedSuitGoods)){
                return;
            }
            suitFixedSuitRule.setFixedSuitGoodsList(suitFixedSuitGoods);
        }
    }
}
