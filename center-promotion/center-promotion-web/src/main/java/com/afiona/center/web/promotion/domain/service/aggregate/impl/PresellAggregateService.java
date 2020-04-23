package com.afiona.center.web.promotion.domain.service.aggregate.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.afiona.center.client.promotion.domain.aggregate.PresellAggregate;
import com.afiona.center.client.promotion.domain.model.promotionrule.presell.PresellGoods;
import com.afiona.center.client.promotion.domain.model.promotionrule.presell.PresellRule;
import com.afiona.center.web.promotion.domain.service.aggregate.IAggregate;
import com.afiona.center.web.promotion.domain.service.aggregate.IBaseAggregate;
import com.afiona.center.web.promotion.infrastructure.dao.PresellGoodsDAO;
import com.afiona.center.web.promotion.infrastructure.dao.PresellRuleDAO;
import com.afiona.center.web.promotion.infrastructure.model.PresellGoodsDO;
import com.afiona.center.web.promotion.infrastructure.model.PresellRuleDO;
import com.afiona.common.util.CloneUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.afiona.center.client.promotion.util.GenericHelper.getDOList;

/**
 * 预售聚合器
 *
 * @author LiJinXing
 * @date 2020/4/22
 */
@Service
public class PresellAggregateService  implements IAggregate<PresellAggregate> {

    @Resource
    private IBaseAggregate<PresellAggregate> baseAggregateService;

    @Resource
    private PresellRuleDAO presellRuleDAO;

    @Resource
    private PresellGoodsDAO presellGoodsDAO;

    @Override
    public void aggregate(List<PresellAggregate> promotionAggregate) {
         if(CollectionUtil.isEmpty(promotionAggregate)){
             return;
         }
         baseAggregateService.aggregate(promotionAggregate);
         aggregatePresellRule(promotionAggregate);
    }

    private void aggregatePresellRule(List<PresellAggregate> promotionAggregate) {
        List<PresellRuleDO> doList = getDOList(promotionAggregate, presellRuleDAO);
        if(CollectionUtil.isEmpty(doList)){
            return;
        }
        List<PresellRule> rules = CloneUtil.cloneList(doList, PresellRule.class);
        Map<Long, PresellRule> ruleMap = rules.stream().collect(Collectors.toMap(PresellRule::getPromotionId, Function.identity()));
        for (PresellAggregate presellAggregate : promotionAggregate) {
            PresellRule presellRule = ruleMap.get(presellAggregate.getId());
            if(presellRule==null){
                continue;
            }
            presellAggregate.setPresellRule(presellRule);
        }
        aggregatePresellGoods(promotionAggregate,rules);
    }

    private void aggregatePresellGoods(List<PresellAggregate> promotionAggregate, List<PresellRule> rules) {
        List<PresellGoodsDO> doList = getDOList(promotionAggregate, presellGoodsDAO);
        if(CollectionUtil.isEmpty(doList)){
            return;
        }
        List<PresellGoods> presellGoodsList = CloneUtil.cloneList(doList, PresellGoods.class);
        Map<Long, List<PresellGoods>> map = presellGoodsList.stream().collect(Collectors.groupingBy(PresellGoods::getPromotionId));
        for (PresellRule rule : rules) {
            List<PresellGoods> presellGoods = map.get(rule.getPromotionId());
            if(CollectionUtil.isEmpty(presellGoods)){
                continue;
            }
            rule.setPresellGoodsList(presellGoods);
        }
    }
}
