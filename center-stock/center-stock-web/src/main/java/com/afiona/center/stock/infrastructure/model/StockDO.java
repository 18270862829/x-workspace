package com.afiona.center.stock.infrastructure.model;

import com.afiona.common.model.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * 库存DO
 *
 * @author dengweiyi
 * @date 2020-02-25
 */
@Data
@TableName(value = "stock")
public class StockDO extends SuperEntity {
    /**
     * SKU ID
     */
    @ApiModelProperty("sku id")
    private Long skuId;

    /**
     * 渠道ID
     */
    @ApiModelProperty("店铺ID")
    private Long storeId;

    /**
     * 可用库存
     */
    @ApiModelProperty("可用库存")
    private Integer available;

    /**
     * 冻结库存
     */
    @ApiModelProperty("冻结库存")
    private Integer frozen;
}
