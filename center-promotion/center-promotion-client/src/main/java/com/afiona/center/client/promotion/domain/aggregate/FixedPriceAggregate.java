package com.afiona.center.client.promotion.domain.aggregate;

import com.afiona.center.client.promotion.domain.model.promotionrule.fixedprice.FixedPriceRule;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 一口价促销活动聚合更
 *
 * @author LiJinXing
 * @date 2020/2/13
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("一口价促销活动聚合更")
public class FixedPriceAggregate extends PromotionAggregate {

    /**
     * 一口价规则
     */
    @ApiModelProperty("一口价规则")
    private FixedPriceRule fixedPriceRule;
}
