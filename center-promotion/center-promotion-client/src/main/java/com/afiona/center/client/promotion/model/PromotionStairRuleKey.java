package com.afiona.center.client.promotion.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 促销活动阶梯满减规则key
 *
 * @author dengweiyi
 * @date 2020-02-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class PromotionStairRuleKey {
    /**
     * 促销活动ID
     */
    private Long promotionId;

    /**
     * 阶梯满减规则ID
     */
    private Long stairRuleId;
}
