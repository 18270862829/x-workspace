package com.afiona.center.web.promotion.infrastructure.model;

import com.afiona.center.client.promotion.constants.choice.DiscountChoiceEnum;
import com.afiona.center.client.promotion.util.IPromotion;
import com.afiona.common.model.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 限时特价促销活动关联商品DO
 *
 * @author LiJinXing
 * @date 2020/2/18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("promotion_special_discount_goods")
public class SpecialDiscountGoodsDO extends SuperEntity implements IPromotion {

    /**
     * 促销活动ID
     */
    private Long promotionId;

    /**
     * SKU ID
     */
    private Long skuId;

    /**
     * 折扣类型选择
     */
    private DiscountChoiceEnum discountChoice;

    /**
     * 减x元
     */
    private BigDecimal amount;

    /**
     * 折扣
     */
    private BigDecimal discount;

    /**
     * 折后x元
     */
    private BigDecimal priceAfterDiscount;
}
