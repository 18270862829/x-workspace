package com.afiona.center.client.promotion.domain.model.promotionrule.seckill;

import com.afiona.center.client.promotion.constants.choice.IgnoreSmallChangeChoiceEnum;
import com.afiona.center.client.promotion.constants.choice.OrderCancellationChoiceEnum;
import com.afiona.center.client.promotion.constants.choice.TheSettlementProcessChoiceEnum;
import com.afiona.common.model.SuperEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * 秒杀
 *
 * @author LiJinXing
 * @date 2020/4/7
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SecondsKillRule extends SuperEntity {

    /**
     * 促销活动id
     */
    private Long promotionId;

    /**
     * 结算流程选择
     */
    private TheSettlementProcessChoiceEnum theSettlementProcessChoice;

    /**
     * 订单取消选择
     */
    private OrderCancellationChoiceEnum orderCancellationChoice;

    /**
     * 秒杀-参与商品
     */
    private List<SecondsKillGoods> secondsKillGoodsList;

    /**
     * 全局设置
     */
    private GlobalDiscountSetting globalDiscountSetting;

    @Data
    @Accessors(chain = true)
    public static class GlobalDiscountSetting{

        /**
         * 价格x元
         */
        private BigDecimal amount;

        /**
         * 订单x件
         */
        private Long quantityStock;

        /**
         * 抹零选择
         */
        private IgnoreSmallChangeChoiceEnum ignoreSmallChangeChoice;
    }
}
