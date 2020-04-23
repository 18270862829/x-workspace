package com.afiona.center.client.promotion.model;

import com.afiona.center.client.promotion.constants.choice.SuitChoiceEnum;
import com.afiona.center.client.promotion.domain.model.Promotion;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 活动关联商品
 *
 * @author LiJinXing
 * @date 2020/4/14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PromotionSuitGoods extends Promotion {

    /**
     * 适用商品类型
     */
    @ApiModelProperty("适用商品类型")
    private SuitChoiceEnum suitGoodsChoice;

    /**
     * 商品sku列表
     */
    @ApiModelProperty("适用商品类型")
    private List<Long> skuIds;
}
