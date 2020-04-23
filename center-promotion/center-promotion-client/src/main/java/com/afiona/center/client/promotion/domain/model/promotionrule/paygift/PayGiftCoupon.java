package com.afiona.center.client.promotion.domain.model.promotionrule.paygift;

import com.afiona.common.model.SuperEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 支付有礼关联赠品 优惠券
 *
 * @author LiJinXing
 * @date 2020/2/25
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("支付有礼关联赠品 优惠券")
public class PayGiftCoupon extends SuperEntity {

    /**
     * 促销活动ID
     */
    private Long promotionId;

    /**
     * 阶段满减规则ID
     */
    private Long stairRuleId;

    /**
     * 优惠劵模版ID
     */
    private Long couponTemplateId;
}
