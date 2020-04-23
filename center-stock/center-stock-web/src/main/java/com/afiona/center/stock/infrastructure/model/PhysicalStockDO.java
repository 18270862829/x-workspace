package com.afiona.center.stock.infrastructure.model;

import com.afiona.common.model.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * 实物库存DO
 *
 * @author dengweiyi
 * @date 2020-02-25
 */
@Data
@TableName(value = "physical_stock")
public class PhysicalStockDO extends SuperEntity {
    /**
     * SKU ID
     */
    private Long skuId;

    /**
     * 仓库ID
     */
    private Long warehouseId;

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
