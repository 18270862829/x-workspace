package com.afiona.center.web.promotion.infrastructure.model;

import com.afiona.center.client.promotion.util.IPromotion;
import com.afiona.common.model.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 固定套装关联商品DO
 *
 * @author LiJinXing
 * @date 2020/2/19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("promotion_suit_fixed_suit_goods")
public class SuitFixedSuitGoodsDO extends SuperEntity implements IPromotion {

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
