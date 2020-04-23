package com.afiona.center.client.promotion.domain.model.promotionrule.suit;

import com.afiona.center.client.promotion.constants.SuitType;
import com.afiona.common.model.SuperEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * 组合套装规则
 *
 * @author LiJinXing
 * @date 2020/2/19
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("组合套装规则")
public class SuitGroupRule extends SuperEntity {

    /**
     * 促销活动ID
     */
    private Long promotionId;

    /**
     * 套装规则
     */
    private SuitType suitType;

    /**
     * 固定套装规则
     */
    private List<SuitFixedSuitRule> suitFixedSuitRules;
}
