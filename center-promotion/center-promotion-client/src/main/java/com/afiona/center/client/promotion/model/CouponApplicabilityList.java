package com.afiona.center.client.promotion.model;

import com.afiona.center.client.promotion.domain.model.Promotion;
import com.afiona.center.client.promotion.domain.model.promotionrule.coupon.CouponTemplate;
import com.afiona.center.client.promotion.domain.model.promotionrule.fullreduction.FullReductionStairRule;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * 优惠券适用性
 *
 * @author LiJinXing
 * @date 2020/3/6
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class  CouponApplicabilityList {

    /**
     * 可用的优惠券
     */
    private List<Applicable> applicableList;

    /**
     * 不可用的优惠券
     */
    private List<NoApplicable> noApplicableList;

    @Data
    public static class NoApplicable{

        /**
         * 优惠券基础信息
         */
        private Promotion promotion;

        /**
         * 适用该优惠券的 商品列表
         */
        private List<Long> skuIds;

        /**
         * 不可用原因
         */
        private String reason;
    }

    @Data
    public static class Applicable implements Comparable<Applicable>{

        /**
         * 优惠券基础信息
         */
        private Promotion promotion;

        /**
         * 适用该优惠券的 商品列表
         */
        private List<Long> skuIds;

        private Boolean availability;

        /**
         * 优惠金额
         */
        private BigDecimal preferentialAmount;

        @Override
        public int compareTo(Applicable o) {
            if(o.preferentialAmount!=null){
                return o.preferentialAmount.compareTo(this.preferentialAmount);
            }
           return 0;
        }
    }
}
