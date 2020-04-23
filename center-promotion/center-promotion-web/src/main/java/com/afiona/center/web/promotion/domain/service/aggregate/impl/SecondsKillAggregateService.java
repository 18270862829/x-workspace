package com.afiona.center.web.promotion.domain.service.aggregate.impl;

import com.afiona.center.client.promotion.domain.aggregate.SecondsKillAggregate;
import com.afiona.center.client.promotion.domain.model.promotionrule.seckill.SecondsKillGoods;
import com.afiona.center.client.promotion.domain.model.promotionrule.seckill.SecondsKillRule;
import com.afiona.center.web.promotion.domain.service.aggregate.IAggregate;
import com.afiona.center.web.promotion.domain.service.aggregate.IBaseAggregate;
import com.afiona.center.web.promotion.infrastructure.dao.SecondsKillGoodsDAO;
import com.afiona.center.web.promotion.infrastructure.dao.SecondsKillRuleDAO;
import com.afiona.center.web.promotion.infrastructure.model.SecondsKillGoodsDO;
import com.afiona.center.web.promotion.infrastructure.model.SecondsKillRuleDO;
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
 * 秒杀聚合器
 *
 * @author LiJinXing
 * @date 2020/4/7
 */
@Service
public class SecondsKillAggregateService  implements IAggregate<SecondsKillAggregate> {

    @Resource
    private IBaseAggregate<SecondsKillAggregate> baseAggregateService;

    @Resource
    private SecondsKillGoodsDAO secondsKillGoodsDAO;

    @Resource
    private SecondsKillRuleDAO secondsKillRuleDAO;

    @Override
    public void aggregate(List<SecondsKillAggregate> promotions) {
        if(CollectionUtil.isEmpty(promotions)){
            return;
        }
        baseAggregateService.aggregate(promotions);
        aggregateSecondsKillRule(promotions);
    }

    private void aggregateSecondsKillRule(List<SecondsKillAggregate> promotions) {
        List<SecondsKillRuleDO> ruleDOList = getDOList(promotions, secondsKillRuleDAO);
        List<SecondsKillRule> rules = CloneUtil.cloneList(ruleDOList, SecondsKillRule.class);
        if(CollectionUtil.isEmpty(rules)){
            return;
        }
        Map<Long, SecondsKillRule> ruleMap = rules.stream().collect(Collectors.toMap(SecondsKillRule::getPromotionId, Function.identity()));
        for (SecondsKillAggregate secondsKillAggregate : promotions) {
            SecondsKillRule secondsKillRule = ruleMap.get(secondsKillAggregate.getId());
            if(secondsKillRule==null){
                continue;
            }
            secondsKillAggregate.setSecondsKillRule(secondsKillRule);
        }

        Map<Long, SecondsKillRuleDO> map = ruleDOList.stream().collect(Collectors.toMap(SecondsKillRuleDO::getId, Function.identity()));
        for (SecondsKillRule secondsKillRule : rules) {
            SecondsKillRuleDO secondsKillRuleDO = map.get(secondsKillRule.getId());
            if(secondsKillRuleDO==null){
                continue;
            }
            secondsKillRule.setGlobalDiscountSetting(toGlobalDiscountSetting(secondsKillRule));
        }
        aggregateSecondsKillGoods(promotions,rules);

    }

    private void aggregateSecondsKillGoods(List<SecondsKillAggregate> promotions, List<SecondsKillRule> rules) {
        List<SecondsKillGoodsDO> doList = getDOList(promotions, secondsKillGoodsDAO);
        List<SecondsKillGoods> secondsKillGoodsList = CloneUtil.cloneList(doList, SecondsKillGoods.class);
        Map<Long, List<SecondsKillGoods>> map = secondsKillGoodsList.stream().collect(Collectors.groupingBy(SecondsKillGoods::getPromotionId));
        for (SecondsKillRule secondsKillRule : rules) {
            List<SecondsKillGoods> secondsKillGoods = map.get(secondsKillRule.getPromotionId());
            if(secondsKillGoods==null){
                continue;
            }
            secondsKillRule.setSecondsKillGoodsList(secondsKillGoods);
        }
    }

    private SecondsKillRule.GlobalDiscountSetting toGlobalDiscountSetting(SecondsKillRule secondsKillRule) {
        return CloneUtil.clone(secondsKillRule.getGlobalDiscountSetting(),SecondsKillRule.GlobalDiscountSetting.class);
    }
}
