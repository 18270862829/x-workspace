package com.afiona.center.web.promotion.infrastructure.model;

import com.afiona.center.client.promotion.util.IPromotion;
import com.afiona.common.model.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 加价购换购商品DO
 *
 * @author LiJinXing
 * @date 2020/2/22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("promotion_add_money_to_buy_goods")
public class AddMoneyToBuyGoodsDO extends SuperEntity implements IPromotion {

    /**
     * 促销活动ID
     */
    private Long promotionId;

    /**
     * SKU ID
     */
    private Long skuId;

    /**
     * 加购价
     */
    private BigDecimal discountsPrice;
}
