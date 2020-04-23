package com.afiona.center.client.promotion.domain.model.promotionrule.specialdiscount;

import com.afiona.center.client.promotion.constants.choice.GlobalDiscountSettingChoice;
import com.afiona.center.client.promotion.constants.choice.IgnoreSmallChangeChoiceEnum;
import com.afiona.common.model.SuperEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * 限时折扣规则
 *
 * @author dengweiyi
 * @date 2020-02-06
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("限时折扣规则")
public class SpecialDiscountRule extends SuperEntity {
    /**
     * 促销活动ID
     */
    private Long promotionId;

    /**
     * 抹零选择
     */
    private IgnoreSmallChangeChoiceEnum ignoreSmallChangeChoice;

    /**
     * 全局设置
     */
    private GlobalDiscountSetting globalDiscountSetting;

    /**
     * 活动商品列表
     */
    private List<SpecialDiscountGoods> specialDiscountGoods;

    @Data
    @Accessors(chain = true)
    public static class GlobalDiscountSetting{
        /**
         * 选择
         */
        private GlobalDiscountSettingChoice choice;

        /**
         * 减x元
         */
        private BigDecimal amount;

        /**
         * 折扣
         */
        private BigDecimal discount;

        /**
         * 折后x元
         */
        private BigDecimal afterAmount;
    }


}
