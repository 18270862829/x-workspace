package com.afiona.center.client.promotion.domain.aggregate;

import com.afiona.center.client.promotion.domain.model.promotionrule.suit.SuitGroupRule;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 组合套装聚合根
 *
 * @author LiJinXing
 * @date 2020/2/19
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("组合套装聚合根")
public class SuitGroupAggregate extends PromotionAggregate{

    /**
     * 组合套装规则
     */
    @ApiModelProperty("组合套装规则")
    private SuitGroupRule suitGroupRule;
}
