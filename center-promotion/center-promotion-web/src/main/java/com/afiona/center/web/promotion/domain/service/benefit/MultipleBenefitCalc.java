package com.afiona.center.web.promotion.domain.service.benefit;

import com.afiona.center.client.promotion.domain.model.Promotion;
import com.afiona.center.client.promotion.model.Goods;
import com.afiona.center.client.promotion.model.MaterialBenefits;
import com.afiona.common.pojo.JsonResult;

import java.util.List;

/**
 * 促销活动福利
 *
 * @author dengweiyi
 * @date 2020-02-11
 */
public interface MultipleBenefitCalc {
    /**
     * 计算
     *
     * @param promotion
     * @param goodsList
     * @return : com.afiona.common.pojo.JsonResult<com.afiona.center.client.promotion.model.PromotionBenefits>
     */
    JsonResult calcBenefits(Promotion promotion, List<Goods> goodsList, MaterialBenefits materialBenefits);

}
