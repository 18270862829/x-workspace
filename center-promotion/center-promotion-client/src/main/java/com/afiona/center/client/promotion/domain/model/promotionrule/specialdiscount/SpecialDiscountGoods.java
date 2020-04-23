package com.afiona.center.client.promotion.domain.model.promotionrule.specialdiscount;

import com.afiona.center.client.promotion.constants.choice.DiscountChoiceEnum;
import com.afiona.common.model.SuperEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 限时特价促销活动关联商品
 *
 * @author dengweiyi
 * @date 2020-02-06
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("限时特价促销活动关联商品")
public class SpecialDiscountGoods extends SuperEntity {
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
