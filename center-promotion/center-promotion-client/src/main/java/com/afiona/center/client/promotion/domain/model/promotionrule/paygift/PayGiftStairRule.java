package com.afiona.center.client.promotion.domain.model.promotionrule.paygift;

import com.afiona.center.client.promotion.constants.choice.GiftChoiceEnum;
import com.afiona.center.client.promotion.constants.choice.GoodsThresholdRuleTypeEnum;
import com.afiona.center.client.promotion.constants.choice.ThresholdChoiceEnum;
import com.afiona.common.model.SuperEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 *  阶梯或循环有礼规则
 *
 * @author LiJinXing
 * @date 2020/2/25
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("阶梯或循环有礼规则")
public class PayGiftStairRule extends SuperEntity implements Comparable<PayGiftStairRule> {

    /**
     * 促销活动ID
     */
    private Long promotionId;

    /**
     * 前x名支付享受活动
     */
    private Integer beforeNum;

    /**
     * 每人限制参与x次
     */
    private Integer number;

    /**
     * 阶梯等级
     */
    private Integer hierarchy;

    /**
     * 优惠门槛
     */
    @ApiModelProperty("支付有礼-优惠门槛")
    private PreferentialThreshold preferentialThreshold;

    /**
     * 送礼选择
     */
    private List<GiftChoiceEnum> giftChoice;

    /**
     * 积分
     */
    private Integer score;

    /**
     * 关联优惠劵列表
     */
    private List<PayGiftCoupon> payGiftCoupons;

    /**
     * 关联赠品列表
     */
    private List<PayGiftGoods> payGiftGoods;

    @Override
    public int compareTo(PayGiftStairRule o) {
        return this.hierarchy-o.hierarchy;
    }

    @Data
    @ApiModel("支付有礼-优惠门槛")
    public static class PreferentialThreshold {

        /**
         * 订单满x元
         */
        private BigDecimal fullOrder;

        /**
         * 商品满x件
         */
        private Integer fullGoods;

        /**
         * 满x件条件选择
         */
        private GoodsThresholdRuleTypeEnum goodsThresholdRuleType;

        /**
         *  满元/满件
         */
        private ThresholdChoiceEnum thresholdChoice;
    }
}
