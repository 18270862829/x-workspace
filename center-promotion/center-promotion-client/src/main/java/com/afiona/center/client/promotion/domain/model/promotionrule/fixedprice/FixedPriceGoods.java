package com.afiona.center.client.promotion.domain.model.promotionrule.fixedprice;

import com.afiona.common.model.SuperEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 一口价促销活动关联商品
 *
 * @author dengweiyi
 * @date 2020-02-06
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("一口价促销活动关联商品")
public class FixedPriceGoods extends SuperEntity {
    /**
     * 促销活动ID
     */
    private Long promotionId;

    /**
     * SKU ID
     */
    private Long skuId;
}
