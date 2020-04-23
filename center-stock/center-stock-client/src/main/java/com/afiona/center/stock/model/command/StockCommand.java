package com.afiona.center.stock.model.command;

import com.afiona.center.stock.model.IdAndCount;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 销售库存操作请求（冻结/解冻、扣减/增加）
 *
 * @author dengweiyi
 * @date 2020-01-06
 */
@Data
@ApiModel("销售库存冻结请求")
public class StockCommand {
    /**
     * 店铺ID
     */
    @ApiModelProperty("店铺ID")
    private long storeId;

    /**
     * 数量列表（SKU ID、sku数量）
     */
    private List<IdAndCount> idAndCountList;
}
