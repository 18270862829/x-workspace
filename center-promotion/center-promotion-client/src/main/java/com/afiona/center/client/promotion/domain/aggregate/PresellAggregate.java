package com.afiona.center.client.promotion.domain.aggregate;

import com.afiona.center.client.promotion.domain.model.promotionrule.presell.PresellRule;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 预售聚合根
 *
 * @author LiJinXing
 * @date 2020/4/22
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("预售聚合根")
public class PresellAggregate extends PromotionAggregate {

    @ApiModelProperty("预售规则")
    private PresellRule presellRule;
}
