package com.afiona.center.web.promotion.infrastructure.model;

import com.afiona.center.client.promotion.constants.choice.LimitTypeChoiceEnum;
import com.afiona.center.client.promotion.constants.choice.MemberDiscountsTypeEnum;
import com.afiona.center.client.promotion.constants.choice.SuitChoiceEnum;
import com.afiona.center.client.promotion.constants.choice.SuperimposedTypeEnum;
import com.afiona.center.client.promotion.util.IPromotion;
import com.afiona.common.model.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 适用规则DO
 *
 * @author dengweiyi
 * @date 2020-02-06
 */
@Data
@TableName("promotion_suit_rule")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SuitRuleDO extends SuperEntity implements IPromotion{
    /**
     * 促销活动ID
     */
    private Long promotionId;

    /**
     * 适用渠道选择
     */
    private SuitChoiceEnum suitChannelChoice;

    /**
     * 适用对象选择
     */
    private SuitChoiceEnum suitMemberChoice;

    /**
     * 适用商品选择
     */
    private SuitChoiceEnum suitGoodsChoice;

    /**
     * 限购次数
     */
    private Integer limitTimes;

    /**
     * 限购类型
     */
    private LimitTypeChoiceEnum limitTypeChoice;

}
