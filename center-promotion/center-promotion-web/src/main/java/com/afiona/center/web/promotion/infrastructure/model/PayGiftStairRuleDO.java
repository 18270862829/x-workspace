package com.afiona.center.web.promotion.infrastructure.model;

import com.afiona.center.client.promotion.constants.choice.GoodsThresholdRuleTypeEnum;
import com.afiona.center.client.promotion.constants.choice.ThresholdChoiceEnum;
import com.afiona.center.client.promotion.util.IPromotion;
import com.afiona.common.model.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 支付有礼循环或阶梯规则DO
 *
 * @author LiJinXing
 * @date 2020/2/25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("promotion_pay_gift_stair_rule")
public class PayGiftStairRuleDO extends SuperEntity implements IPromotion {

    /**
     * 促销活动ID
     */
    private Long promotionId;

    /**
     * 阶梯等级
     */
    private Integer hierarchy;

    /**
     * 前x名支付享受活动
     */
    private Integer beforeNum;

    /**
     * 每人限制参与x次
     */
    private Integer number;

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
     * 满元/满件选择
     */
    private ThresholdChoiceEnum thresholdChoice;

    /**
     * 送礼选择
     */
    private String giftChoice;

    /**
     * 积分
     */
    private Integer score;
}
