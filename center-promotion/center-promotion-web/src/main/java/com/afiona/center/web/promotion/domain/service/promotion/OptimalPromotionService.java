package com.afiona.center.web.promotion.domain.service.promotion;

import com.afiona.center.client.promotion.domain.model.Promotion;
import com.afiona.common.pojo.JsonResult;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 最优促销活动service
 *
 * @author LiJinXing
 * @date 2020/3/26
 */
public interface OptimalPromotionService {

    Promotion optimalPromotion(Long skuId);

    Map<Long, Promotion> optimalPromotion(List<Long> skuIds);

    List<Promotion> promotionListBySkuId(Long skuId);

    Map<Long, List<Promotion>> promotionListBySkuIds(List<Long> skuIds);
}
