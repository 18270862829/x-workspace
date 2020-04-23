package com.afiona.center.client.promotion.model;

import com.afiona.center.client.promotion.domain.model.Promotion;

import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * 促销活动福利
 *
 * @author dengweiyi
 * @date 2020-02-10
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class PromotionBenefits extends Promotion{

    /**
     * 福利
     */
    private Benefits benefits;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Benefits{

        /**
         * 促销活动 优惠金额
         */
        private BigDecimal preferentialAmount;

        /**
         * 赠送积分
         */
        private int score;

        /**
         * 赠送优惠劵列表
         */
        private List<Long> couponTemplateIdList;

        /**
         * 赠品列表
         */
        private List<Long> giftSkuIds;

        /**
         * 包邮
         */
        private boolean freeShipping;
    }
}
