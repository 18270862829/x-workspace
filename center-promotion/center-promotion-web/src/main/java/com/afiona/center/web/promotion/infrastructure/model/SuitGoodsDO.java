package com.afiona.center.web.promotion.infrastructure.model;

import com.afiona.center.client.promotion.util.IPromotion;
import com.afiona.common.model.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 适用商品DO
 *
 * @author dengweiyi
 * @date 2020-02-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("promotion_suit_goods")
public class SuitGoodsDO extends SuperEntity implements IPromotion{
    /**
     * 促销活动ID
     */
    private Long promotionId;

    /**
     * SKU ID
     */
    private Long skuId;
}
