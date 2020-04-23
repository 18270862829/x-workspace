package com.afiona.center.client.promotion.model.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * 优惠券福利计算
 *
 * @author LiJinXing
 * @date 2020/3/2
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class CouponBenefitCalcQuery {

    /**
     * 优惠券模板id
     */
    private Long couponTemplateId;

    /**
     * 关联商品列表
     */
    private List<RelatedGoods> relatedGoods;

    @Data
    public static class RelatedGoods{

        /**
         * SKU ID
         */
        private long skuId;

        /**
         * 价格
         */
        private BigDecimal price;

    }
}
