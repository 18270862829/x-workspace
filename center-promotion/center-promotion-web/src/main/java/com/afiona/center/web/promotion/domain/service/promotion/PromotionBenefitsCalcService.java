package com.afiona.center.web.promotion.domain.service.promotion;

import com.afiona.center.client.promotion.model.Benefit;
import com.afiona.center.client.promotion.model.MaterialBenefits;
import com.afiona.center.client.promotion.model.query.BenefitCalcQuery;
import com.afiona.common.pojo.JsonResult;

import java.util.List;

/**
 * 促销活动福利计算service
 *
 * @author LiJinXing
 * @date 2020/3/26
 */
public interface PromotionBenefitsCalcService {

    /**
     * 促销福利计算
     *
     * @param query 福利计算需求条件
     * @return 福利信息
     */
    JsonResult<MaterialBenefits> benefitsCalc(BenefitCalcQuery query);
}
