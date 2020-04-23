package com.afiona.center.web.promotion.domain.service.aggregate.impl;

import cn.hutool.core.util.ObjectUtil;
import com.afiona.center.client.promotion.domain.aggregate.AddMoneyToBuyAggregate;
import com.afiona.center.client.promotion.domain.aggregate.FixedPriceAggregate;
import com.afiona.center.client.promotion.domain.model.promotionrule.fixedprice.FixedPriceGoods;
import com.afiona.center.client.promotion.domain.model.promotionrule.fixedprice.FixedPriceRule;
import com.afiona.center.web.promotion.domain.service.aggregate.IAggregate;
import com.afiona.center.web.promotion.domain.service.aggregate.IBaseAggregate;
import com.afiona.center.web.promotion.infrastructure.dao.FixedPriceGoodsDAO;
import com.afiona.center.web.promotion.infrastructure.dao.FixedPriceRuleDAO;
import com.afiona.center.web.promotion.infrastructure.model.FixedPriceGoodsDO;
import com.afiona.center.web.promotion.infrastructure.model.FixedPriceRuleDO;
import com.afiona.common.util.CloneUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.deepexi.util.CollectionUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.afiona.center.client.promotion.util.GenericHelper.getDOList;

/**
 * 一口价促销活动聚合器
 *
 * @author LiJinXing
 * @date 2020/2/13
 */
@Service
public class FixedPriceAggregateService  implements IAggregate<FixedPriceAggregate> {

    @Resource
    private IBaseAggregate<FixedPriceAggregate> baseAggregateService;

    @Resource
    private FixedPriceRuleDAO fixedPriceRuleDAO;

    @Resource
    private FixedPriceGoodsDAO fixedPriceGoodsDAO;

    @Override
    public void aggregate(List<FixedPriceAggregate> promotions){
        if(CollectionUtil.isEmpty(promotions)){
            return;
        }
        baseAggregateService.aggregate(promotions);
        aggregateFixedPriceRule(promotions);
    }

    private void aggregateFixedPriceRule(List<FixedPriceAggregate> promotions) {
        List<FixedPriceRuleDO> dos = getDOList(promotions, fixedPriceRuleDAO);
        List<FixedPriceRule> fixedPriceRules = CloneUtil.cloneList(dos, FixedPriceRule.class);
        for (FixedPriceRule fixedPriceRule : fixedPriceRules) {
            List<FixedPriceGoodsDO> fixedPriceGoodsDOS = fixedPriceGoodsDAO.list(new LambdaQueryWrapper<FixedPriceGoodsDO>().in(FixedPriceGoodsDO::getPromotionId, fixedPriceRule.getPromotionId()));
            List<FixedPriceGoods> fixedPriceGoodsList = CloneUtil.cloneList(fixedPriceGoodsDOS, FixedPriceGoods.class);
            fixedPriceRule.setFixedPriceGoods(fixedPriceGoodsList);
        }
        Map<Long, List<FixedPriceRule>> fixedPriceRuleMap = fixedPriceRules.stream().collect(Collectors.groupingBy(FixedPriceRule::getPromotionId,Collectors.toList()));
        for (FixedPriceAggregate fixedPriceAggregate : promotions) {
            List<FixedPriceRule> fixedPriceRule = fixedPriceRuleMap.get(fixedPriceAggregate.getId());
            if(ObjectUtil.isNull(fixedPriceRule)){
                continue;
            }
            fixedPriceAggregate.setFixedPriceRule(fixedPriceRule.get(fixedPriceRule.size()-1));
        }
    }

}
