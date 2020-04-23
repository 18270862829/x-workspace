package com.afiona.center.client.promotion.domain.aggregate;

import com.afiona.center.client.promotion.domain.model.Promotion;
import com.afiona.center.client.promotion.domain.model.equities.Equities;
import com.afiona.center.client.promotion.domain.model.suitrule.SuitRule;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 活动规则聚合根
 *
 * @author dengweiyi
 * @date 2019-12-31
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("活动规则聚合根")
public class PromotionAggregate extends Promotion {

    /**
     * 适用规则
     */
    @ApiModelProperty("适用规则")
    private SuitRule suitRule;

    /**
     * 权益
     */
    @ApiModelProperty("权益")
    private Equities equities;

}
