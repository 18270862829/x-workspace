package com.afiona.center.client.promotion.domain.model.promotionrule.paygift;

import com.afiona.common.model.SuperEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 支付有礼关联 赠品
 *
 * @author LiJinXing
 * @date 2020/2/25
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("支付有礼关联 赠品")
public class PayGiftGoods extends SuperEntity {

    /**
     * 促销活动ID
     */
    private Long promotionId;

    /**
     * 阶段满减规则ID
     */
    private Long stairRuleId;

    /**
     * 赠品ID
     */
    private Long skuId;
}
