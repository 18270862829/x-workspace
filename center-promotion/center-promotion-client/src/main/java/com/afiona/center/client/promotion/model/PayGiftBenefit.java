package com.afiona.center.client.promotion.model;

import com.afiona.center.client.promotion.domain.model.Promotion;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * 支付有礼福利
 *
 * @author LiJinXing
 * @date 2020/3/30
 */
@Data
@Accessors(chain = true)
public class PayGiftBenefit {

    /**
     * 活动基础信息
     */
    private Promotion promotion;

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
