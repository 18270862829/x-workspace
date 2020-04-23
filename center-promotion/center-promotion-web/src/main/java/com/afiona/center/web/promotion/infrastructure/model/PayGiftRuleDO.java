package com.afiona.center.web.promotion.infrastructure.model;

import com.afiona.center.client.promotion.constants.choice.GiftChoiceEnum;
import com.afiona.center.client.promotion.constants.choice.PayGiftTypeEnum;
import com.afiona.center.client.promotion.util.IPromotion;
import com.afiona.common.model.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 支付有礼规则DO
 *
 * @author LiJinXing
 * @date 2020/2/25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("promotion_pay_gift_rule")
public class PayGiftRuleDO extends SuperEntity implements IPromotion {

    /**
     * 促销活动ID
     */
    private Long promotionId;

    /**
     * 优惠类型
     */
    private PayGiftTypeEnum payGiftType;

}
