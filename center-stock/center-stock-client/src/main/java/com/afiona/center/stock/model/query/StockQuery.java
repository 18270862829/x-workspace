package com.afiona.center.stock.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 销售库存查询条件
 *
 * @author dengweiyi
 * @date 2020-01-06
 */
@Data
@ApiModel("销售库存查询条件")
public class StockQuery {
    /**
     * 店铺ID
     */
    @ApiModelProperty("店铺ID")
    private Long storeId;

    /**
     * SKU ID列表
     */
    @ApiModelProperty("SKU ID列表")
    private List<Long> skuIds;
}
