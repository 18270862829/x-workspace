package com.afiona.center.web.promotion.infrastructure.model;

import com.afiona.center.client.promotion.util.IPromotion;
import com.afiona.common.model.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 一口价关联折扣商品列表DO
 *
 * @author LiJinXing
 * @date 2020/2/13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("promotion_fixed_price_goods")
public class FixedPriceGoodsDO extends SuperEntity implements IPromotion {

    /**
     * 促销活动ID
     */
    private Long promotionId;

    /**
     * SKU ID
     */
    private Long skuId;
}
