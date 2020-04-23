package com.afiona.center.web.promotion.infrastructure.model;

import com.afiona.center.client.promotion.constants.choice.DiscountsNumberEnum;
import com.afiona.center.client.promotion.constants.choice.FullNumChoiceEnum;
import com.afiona.center.client.promotion.constants.choice.ThresholdChoiceEnum;
import com.afiona.center.client.promotion.util.IPromotion;
import com.afiona.common.model.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 加价购规则DO
 *
 * @author LiJinXing
 * @date 2020/2/22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("promotion_add_money_to_buy_rule")
public class AddMoneyToBuyRuleDO extends SuperEntity implements IPromotion {

    /**
     * 促销活动ID
     */
    private Long promotionId;

    /**
     * 优惠门槛选择
     */
    private ThresholdChoiceEnum thresholdChoice;

    /**
     * 满x元
     */
    private BigDecimal fullAmount;

    /**
     * 满x件条件选择
     */
    private FullNumChoiceEnum fullNumChoice;

    /**
     * 满x件
     */
    private Integer fullNum;

    /**
     * 换购数量
     */
    private DiscountsNumberEnum discountsNumber;
}
