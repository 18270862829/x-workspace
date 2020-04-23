package com.afiona.center.client.promotion.domain.model.promotionrule.suit;

import com.afiona.common.model.SuperEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 固定套装关联商品
 *
 * @author LiJinXing
 * @date 2020/2/19
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("固定套装关联商品")
public class SuitFixedSuitGoods extends SuperEntity {

    /**
     * 促销活动ID
     */
    private Long promotionId;

    /**
     * 固定套装规则id
     */
    private Long suitFixedSuitId;

    /**
     * SKU ID
     */
    private Long skuId;

    /**
     * 数量
     */
    private Long number;

}
