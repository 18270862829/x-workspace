package com.afiona.center.client.promotion.domain.model.promotionrule.fullreduction;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import com.afiona.center.client.promotion.constants.PreferentialType;
import com.afiona.center.client.promotion.constants.PromotionResultEnum;
import com.afiona.center.client.promotion.model.Benefit;
import com.afiona.common.model.SuperEntity;
import com.afiona.common.pojo.JsonResult;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 满减规则
 *
 * @author dengweiyi
 * @date 2020-02-05
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("满减规则")
public class FullReductionRule extends SuperEntity {

    /**
     * 促销活动ID
     */
    private Long promotionId;

    /**
     * 优惠类型
     */
    private PreferentialType preferentialType;

    /**
     * 阶梯满减规则
     */
    private List<FullReductionStairRule> fullReductionStairRules;

    /**
     * 准备福利计算参数
     *
     * @param goodsTotalPrice
     * @param goodsNum
     * @return : com.afiona.common.pojo.JsonResult<com.afiona.center.client.promotion.domain.model.promotionrule.fullreduction.FullReductionRule.BenefitParams>
     */
    public JsonResult<Benefit> calcBenefits(BigDecimal goodsTotalPrice, int goodsNum) {
        FullReductionStairRule qualifiedStairRule = null;
        int times = 1;

        if (preferentialType == PreferentialType.STAIR) {
            // 寻找满减阶段
            List<FullReductionStairRule> stairRules = Lists.newArrayList(fullReductionStairRules);
            Collections.reverse(stairRules);
            for (FullReductionStairRule stairRule : stairRules) {
                if (stairRule.getPreferentialThreshold().qualified(goodsTotalPrice, goodsNum)) {
                    qualifiedStairRule = stairRule;
                    break;
                }
            }

            if (qualifiedStairRule == null) {
                return JsonResult.create(PromotionResultEnum.LESS_THAN_THRESHOLD);
            }

        } else {
            qualifiedStairRule = fullReductionStairRules.get(0);
            if (!qualifiedStairRule.getPreferentialThreshold().qualified(goodsTotalPrice, goodsNum)) {
                return JsonResult.create(PromotionResultEnum.LESS_THAN_THRESHOLD);
            }
            // 计算循环满减的次数
            times = qualifiedStairRule.getPreferentialThreshold().times(goodsTotalPrice, goodsNum);
        }
        FullReductionStairRule.BenefitParams benefitParams = new FullReductionStairRule.BenefitParams(goodsTotalPrice, times);
        Benefit benefit = qualifiedStairRule.getPreferentialContent().calcBenefits(benefitParams,preferentialType);
        return JsonResult.create(benefit);
    }
}
