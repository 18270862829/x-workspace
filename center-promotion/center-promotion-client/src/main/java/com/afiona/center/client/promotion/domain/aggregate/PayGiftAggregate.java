package com.afiona.center.client.promotion.domain.aggregate;

import com.afiona.center.client.promotion.domain.model.promotionrule.paygift.PayGiftRule;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 支付有礼聚合根
 *
 * @author LiJinXing
 * @date 2020/2/25
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("支付有礼聚合根")
public class PayGiftAggregate extends PromotionAggregate {
    /**
     * 支付有礼规则
     */
    @ApiModelProperty("支付有礼规则")
    private PayGiftRule payGiftRule;
}
