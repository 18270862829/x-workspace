package com.afiona.center.client.promotion.domain.model.promotionrule.fixedprice;

import com.afiona.center.client.promotion.constants.choice.LimitChoiceEnum;
import com.afiona.common.model.SuperEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * 一口价规则
 *
 * @author dengweiyi
 * @date 2020-02-06
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("一口价规则")
public class FixedPriceRule extends SuperEntity {
    /**
     * 促销活动ID
     */
    private Long promotionId;

    /**
     * x元
     */
    private BigDecimal amount;

    /**
     * 任选x件
     */
    private Integer goodsNum;

    /**
     * 商品限制
     */
    private LimitChoiceEnum limitChoice;

    /**
     * 折扣商品列表
     */
    private List<FixedPriceGoods> fixedPriceGoods;

}
