package com.afiona.center.client.promotion.domain.aggregate;

import com.afiona.center.client.promotion.domain.model.promotionrule.specialdiscount.SpecialDiscountRule;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 限时折扣聚合根
 *
 * @author LiJinXing
 * @date 2020/2/18
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("限时折扣聚合根")
public class SpecialDiscountAggregate extends PromotionAggregate {

    /**
     * 限时折扣规则
     */
    @ApiModelProperty("适用规则")
    private SpecialDiscountRule specialDiscountRule;
}
