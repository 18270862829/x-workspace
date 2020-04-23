package com.afiona.center.web.promotion.infrastructure.model;

import com.afiona.center.client.promotion.constants.SuitType;
import com.afiona.center.client.promotion.util.IPromotion;
import com.afiona.common.model.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;


/**
 * 组合套装规则DO
 *
 * @author LiJinXing
 * @date 2020/2/19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("promotion_suit_group_rule")
public class SuitGroupRuleDO extends SuperEntity implements IPromotion {

    /**
     * 促销活动ID
     */
    private Long promotionId;

    /**
     * 套装规则
     */
    private SuitType suitType;
}
