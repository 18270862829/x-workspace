package com.afiona.center.web.promotion.domain.service.promotion;

import com.afiona.center.client.promotion.domain.aggregate.PromotionAggregate;
import com.afiona.center.client.promotion.model.PromotionSuitGoods;
import com.afiona.common.pojo.JsonResult;

/**
 * 促销商品service
 *
 * @author LiJinXing
 * @date 2020/4/15
 */
public interface PromotionGoodsService {

    JsonResult<PromotionSuitGoods> suitGoods(PromotionAggregate promotionAggregate);
}
