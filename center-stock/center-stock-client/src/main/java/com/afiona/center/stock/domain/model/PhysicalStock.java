package com.afiona.center.stock.domain.model;

import com.afiona.common.model.SuperEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 实物库存
 *
 * @author dengweiyi
 * @date 2020-01-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("实物库存")
public class PhysicalStock extends SuperEntity {
    /**
     * SKU ID
     */
    @ApiModelProperty("SKU ID")
    private Long skuId;

    /**
     * 仓库ID
     */
    @ApiModelProperty("仓库ID")
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
