package com.afiona.center.stock.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ID及数量
 *
 * @author dengweiyi
 * @date 2020-01-06
 */
@Data
@ApiModel("ID及数量")
@AllArgsConstructor
public class IdAndCount {
    /**
     * ID
     */
    @ApiModelProperty("ID")
    private long id;

    /**
     * 数量
     */
    @ApiModelProperty("数量")
    private int count;
}
