package com.afiona.center.client.promotion.domain.aggregate;

import com.afiona.center.client.promotion.domain.model.promotionrule.addmoneytobuy.AddMoneyToBuyRule;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 加价购聚合根
 *
 * @author LiJinXing
 * @date 2020/2/22
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("加价购聚合根")
public class AddMoneyToBuyAggregate extends PromotionAggregate {

    /**
     * 加价购规则
     */
    @ApiModelProperty("加价购规则")
    private AddMoneyToBuyRule addMoneyToBuyRule;

}
