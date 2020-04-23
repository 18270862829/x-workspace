package com.afiona.center.web.promotion.infrastructure.model;

import com.afiona.center.client.promotion.constants.choice.*;
import com.afiona.center.client.promotion.util.IPromotion;
import com.afiona.common.model.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 阶梯满减规则DO
 *
 * @author dengweiyi
 * @date 2020-02-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("promotion_full_reduction_stair_rule")
public class FullReductionStairRuleDO extends SuperEntity implements IPromotion {
    /**
     * 促销活动ID
     */
    private Long promotionId;

    /**
     * 等级
     */
    private Integer level;

    /**
     * 优惠门槛选择
     */
    private ThresholdChoiceEnum thresholdChoice;

    /**
     * 满x元
     */
    private BigDecimal fullAmount;

    /**
     * 满x元条件选择
     */
    private FullPriceChoiceEnum fullPriceChoice;

    /**
     * 满x件
     */
    private Integer fullNum;

    /**
     * 满x件条件选择
     */
    private FullNumChoiceEnum fullNumChoice;

    /**
     * 优惠内容类型
     */
    private String contentTypeChoice;

    /**
     * 金额优惠选择
     */
    private AmountChoiceEnum amountChoice;
    /**
     * 减x元
     */
    private BigDecimal reduceAmount;

    /**
     * 折扣
     */
    private BigDecimal discount;

    /**
     * 封顶
     */
    private BigDecimal limits;

    /**
     * 送礼选择
     */
    private GiftChoiceEnum giftChoice;

    /**
     * 积分
     */
    private Integer score;
}
