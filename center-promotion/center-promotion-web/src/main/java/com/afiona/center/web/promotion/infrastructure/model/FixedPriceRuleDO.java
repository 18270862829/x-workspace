package com.afiona.center.web.promotion.infrastructure.model;

import java.math.BigDecimal;

import com.afiona.center.client.promotion.constants.choice.LimitChoiceEnum;
import com.afiona.center.client.promotion.util.IPromotion;
import com.afiona.common.model.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 一口价规则DO
 *
 * @author LiJinXing
 * @date 2020/2/13
 */
@Data
@TableName("promotion_fixed_price_rule")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class FixedPriceRuleDO extends SuperEntity implements IPromotion {

    /**
     * 促销活动ID
     */
    private Long promotionId;

    /**
     * x元
     */
    private BigDecimal amount;

    /**
     * 任选x件
     */
    private Integer goodsNum;

    /**
     * 商品限制
     */
    private LimitChoiceEnum limitChoice;

}
