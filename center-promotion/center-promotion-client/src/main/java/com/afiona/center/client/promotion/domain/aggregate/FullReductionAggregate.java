package com.afiona.center.client.promotion.domain.aggregate;

import com.afiona.center.client.promotion.domain.model.promotionrule.fullreduction.FullReductionRule;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 满减促销活动聚合根
 *
 * @author dengweiyi
 * @date 2020-02-09
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("满减促销活动聚合根")
public class FullReductionAggregate extends PromotionAggregate {
    /**
     * 满减规则
     */
    @ApiModelProperty("满减规则")
    private FullReductionRule fullReductionRule;
}
