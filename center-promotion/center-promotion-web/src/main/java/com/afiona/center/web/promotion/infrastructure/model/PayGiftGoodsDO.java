package com.afiona.center.web.promotion.infrastructure.model;

import com.afiona.center.client.promotion.util.IPromotion;
import com.afiona.common.model.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 支付有礼关联赠品DO
 *
 * @author LiJinXing
 * @date 2020/2/25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("promotion_pay_gift_goods")
public class PayGiftGoodsDO extends SuperEntity implements IPromotion {

    /**
     * 促销活动ID
     */
    private Long promotionId;

    /**
     * 阶梯或循环有礼规则ID
     */
    private Long stairRuleId;

    /**
     * 赠品ID
     */
    private Long skuId;
}
