package com.afiona.center.web.promotion.domain.service.benefit;

import com.afiona.center.client.promotion.model.SingleCommoditiesBenefit;
import com.afiona.center.client.promotion.model.query.GoodsBenefitCalcQuery;
import com.afiona.common.pojo.JsonResult;

import java.util.List;

/**
 * 单品福利计算
 *
 * @author LiJinXing
 * @date 2020/3/27
 */
public interface GoodsBenefitCalcService {

    JsonResult<List<SingleCommoditiesBenefit>> benefitCalc(GoodsBenefitCalcQuery query);
}
