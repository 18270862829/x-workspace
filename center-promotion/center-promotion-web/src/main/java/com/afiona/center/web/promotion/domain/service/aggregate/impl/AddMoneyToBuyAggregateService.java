package com.afiona.center.web.promotion.domain.service.aggregate.impl;

import com.afiona.center.client.promotion.domain.aggregate.AddMoneyToBuyAggregate;
import com.afiona.center.client.promotion.domain.model.promotionrule.addmoneytobuy.AddMoneyToBuyGoods;
import com.afiona.center.client.promotion.domain.model.promotionrule.addmoneytobuy.AddMoneyToBuyRule;
import com.afiona.center.web.promotion.domain.service.aggregate.IAggregate;
import com.afiona.center.web.promotion.domain.service.aggregate.IBaseAggregate;
import com.afiona.center.web.promotion.infrastructure.dao.AddMoneyToBuyGoodsDAO;
import com.afiona.center.web.promotion.infrastructure.dao.AddMoneyToBuyRuleDAO;
import com.afiona.center.web.promotion.infrastructure.model.AddMoneyToBuyGoodsDO;
import com.afiona.center.web.promotion.infrastructure.model.AddMoneyToBuyRuleDO;
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
 * 加价购聚合器
 *
 * @author LiJinXing
 * @date 2020/2/22
 */
@Service
public class AddMoneyToBuyAggregateService implements IAggregate<AddMoneyToBuyAggregate> {

    @Resource
    private IBaseAggregate<AddMoneyToBuyAggregate> baseAggregateService;

    @Resource
    private AddMoneyToBuyRuleDAO addMoneyToBuyRuleDAO;

    @Resource
    private AddMoneyToBuyGoodsDAO addMoneyToBuyGoodsDAO;

    @Override
    public void aggregate(List<AddMoneyToBuyAggregate> promotions) {
        if(CollectionUtil.isEmpty(promotions)){
            return;
        }
        baseAggregateService.aggregate(promotions);
        aggregateAddMoneyToBuyRule(promotions);
    }

    private void aggregateAddMoneyToBuyRule(List<AddMoneyToBuyAggregate> promotions) {
        List<AddMoneyToBuyRuleDO> ruleDOList = getDOList(promotions, addMoneyToBuyRuleDAO);
        List<AddMoneyToBuyRule> rules = CloneUtil.cloneList(ruleDOList, AddMoneyToBuyRule.class);
        Map<Long, AddMoneyToBuyRule> ruleMap = rules.stream().collect(Collectors.toMap(AddMoneyToBuyRule::getPromotionId, Function.identity()));
        for (AddMoneyToBuyAggregate addMoneyToBuyAggregate : promotions) {
            AddMoneyToBuyRule rule = ruleMap.get(addMoneyToBuyAggregate.getId());
            if(rule==null){
                continue;
            }
            addMoneyToBuyAggregate.setAddMoneyToBuyRule(rule);
        }
        aggregateAddMoneyToBuyGoods(promotions,rules);
    }

    private void aggregateAddMoneyToBuyGoods(List<AddMoneyToBuyAggregate> promotions, List<AddMoneyToBuyRule> rules) {
        List<AddMoneyToBuyGoodsDO> list = getDOList(promotions, addMoneyToBuyGoodsDAO);
        List<AddMoneyToBuyGoods> goodsList = CloneUtil.cloneList(list, AddMoneyToBuyGoods.class);
        Map<Long, List<AddMoneyToBuyGoods>> goodsMap = goodsList.stream().collect(Collectors.groupingBy(AddMoneyToBuyGoods::getPromotionId));
        for (AddMoneyToBuyRule rule : rules) {
            List<AddMoneyToBuyGoods> addMoneyToBuyGoods = goodsMap.get(rule.getPromotionId());
            if(CollectionUtil.isEmpty(addMoneyToBuyGoods)){
                continue;
            }
            rule.setAddMoneyToBuyGoodsList(addMoneyToBuyGoods);
        }
    }
}
