package com.afiona.center.client.promotion.model.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * 优惠券适用性查询
 *
 * @author LiJinXing
 * @date 2020/3/6
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class CouponTemplateQuery {

    /**
     * 优惠券模板id列表
     */
    private List<Long> couponTemplateIds;

    /**
     * 商品skuId列表
     */
    private List<Long> skuIds;

    /**
     * 当前总金额- 经过促销活动优惠后的金额
     */
    private BigDecimal currentTotalAmount;

}
