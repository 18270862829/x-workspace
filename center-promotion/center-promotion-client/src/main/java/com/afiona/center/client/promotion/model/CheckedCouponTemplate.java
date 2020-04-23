package com.afiona.center.client.promotion.model;

import com.afiona.center.client.promotion.domain.aggregate.CouponTemplateAggregate;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 校验后的优惠劵模版
 *
 * @author dengweiyi
 * @date 2020-04-15
 */
@Data
public class CheckedCouponTemplate extends CouponTemplateAggregate {
    /**
     * 不可用原因
     */
    private String reason;

    /**
     * 优惠金额
     */
    private BigDecimal preferentialAmount;

    /**
     * 是否可用
     *
     * @param
     * @return : boolean
     */
    public boolean available(){
        return reason != null;
    }
}
