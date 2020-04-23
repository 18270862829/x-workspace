package com.afiona.center.web.promotion.domain.service.benefit.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.afiona.center.client.promotion.constants.PromotionResultEnum;
import com.afiona.center.client.promotion.domain.aggregate.SuitGroupAggregate;
import com.afiona.center.client.promotion.domain.model.Promotion;
import com.afiona.center.client.promotion.domain.model.promotionrule.suit.SuitFixedSuitRule;
import com.afiona.center.client.promotion.domain.model.promotionrule.suit.SuitGroupRule;
import com.afiona.center.client.promotion.model.PromotionBenefits;
import com.afiona.center.client.promotion.model.query.BenefitCalcQuery;
import com.afiona.center.web.promotion.domain.service.aggregate.impl.SuitGroupAggregateService;
import com.afiona.common.pojo.JsonResult;
import com.afiona.common.util.CloneUtil;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.List;

/**
 * 组合套装活动福利计算
 *
 * @author LiJinXing
 * @date 2020/2/24
 */
@Component
public class SuitGroupBenefitCalc  {

    @Resource
    private SuitGroupAggregateService suitGroupAggregateService;

    public JsonResult<PromotionBenefits.Benefits> calcBenefits(Promotion promotion, BenefitCalcQuery.InvolvedPromotion involvedPromotion) {
        SuitGroupAggregate suitGroupAggregate = CloneUtil.clone(promotion, SuitGroupAggregate.class);
        suitGroupAggregateService.aggregate(Lists.newArrayList(suitGroupAggregate));
        SuitGroupRule suitGroupRule = suitGroupAggregate.getSuitGroupRule();
        List<SuitFixedSuitRule> suitFixedSuitRules = suitGroupRule.getSuitFixedSuitRules();
        if (CollectionUtil.isEmpty(suitFixedSuitRules)) {
            return JsonResult.create(PromotionResultEnum.DATA_ERROR);
        }
        
        return null;
    }

}
