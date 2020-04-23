package com.afiona.center.client.promotion.domain.model.promotionrule.paygift;

import com.afiona.center.client.promotion.constants.choice.PayGiftTypeEnum;
import com.afiona.common.model.SuperEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 支付有礼规则
 *
 * @author LiJinXing
 * @date 2020/2/25
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("支付有礼规则")
public class PayGiftRule extends SuperEntity {

    /**
     * 促销活动ID
     */
    private Long promotionId;

    /**
     * 优惠类型
     */
    private PayGiftTypeEnum payGiftType;

    /**
     * 阶梯或循环有礼规则
     */
     private List<PayGiftStairRule> payGiftStairRules;

}
