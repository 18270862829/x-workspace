package com.afiona.center.client.promotion.model;

import com.afiona.center.client.promotion.domain.model.Promotion;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 促销活动组（1个SKU对应多个促销活动）
 *
 * @author dengweiyi
 * @date 2020-02-14
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class PromotionGroup {
    /**
     * SKU ID
     */
    private Long skuId;

    /**
     * 促销活动列表
     */
    private List<Promotion> promotions;
}
