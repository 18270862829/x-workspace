package com.afiona.center.web.promotion.infrastructure.model;

import com.afiona.center.client.promotion.constants.choice.GlobalDiscountSettingChoice;
import com.afiona.center.client.promotion.constants.choice.IgnoreSmallChangeChoiceEnum;
import com.afiona.center.client.promotion.util.IPromotion;
import com.afiona.common.model.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 *限时折扣规则DO
 *
 * @author LiJinXing
 * @date 2020/2/18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("promotion_special_discount_rule")
public class SpecialDiscountRuleDO extends SuperEntity implements IPromotion {

    /**
     * 促销活动ID
     */
    private Long promotionId;

    /**
     * 抹零选择
     */
    private IgnoreSmallChangeChoiceEnum ignoreSmallChangeChoice;

    /**
     * 选择
     */
    private GlobalDiscountSettingChoice choice;

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
    private BigDecimal afterAmount;
}
