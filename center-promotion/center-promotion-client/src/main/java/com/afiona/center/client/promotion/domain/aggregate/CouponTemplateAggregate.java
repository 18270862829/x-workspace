package com.afiona.center.client.promotion.domain.aggregate;

import com.afiona.center.client.promotion.domain.model.promotionrule.coupon.CouponTemplate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 优惠劵模版促销活动聚合根
 *
 * @author dengweiyi
 * @date 2020-02-09
 */

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("优惠劵模版促销活动聚合根")
public class CouponTemplateAggregate extends PromotionAggregate {
    /**
     * 优惠劵模版
     */
    @ApiModelProperty("优惠劵模版")
    private CouponTemplate couponTemplate;
}
