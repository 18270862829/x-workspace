package com.afiona.center.web.promotion.infrastructure.model;

import java.math.BigDecimal;

import com.afiona.center.client.promotion.constants.PreferentialType;
import com.afiona.center.client.promotion.constants.choice.AmountChoiceEnum;
import com.afiona.center.client.promotion.constants.choice.ContentTypeChoiceEnum;
import com.afiona.center.client.promotion.constants.choice.FullNumChoiceEnum;
import com.afiona.center.client.promotion.constants.choice.FullPriceChoiceEnum;
import com.afiona.center.client.promotion.constants.choice.GiftChoiceEnum;
import com.afiona.center.client.promotion.util.IPromotion;
import com.afiona.common.model.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 满减规则DO
 *
 * @author dengweiyi
 * @date 2020-02-09
 */
@Data
@TableName("promotion_full_reduction_rule")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class FullReductionRuleDO extends SuperEntity implements IPromotion{

    /**
     * 促销活动ID
     */
    private Long promotionId;

    /**
     * 优惠类型
     */
    private PreferentialType preferentialType;

}
