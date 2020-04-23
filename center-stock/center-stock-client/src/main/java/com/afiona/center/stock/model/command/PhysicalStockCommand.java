package com.afiona.center.stock.model.command;
import com.afiona.center.stock.constants.enums.StockFlowType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 实物库存操作请求
 *
 * @author dengweiyi
 * @date 2020-02-25
 */
@Data
@AllArgsConstructor
public class PhysicalStockCommand {
    /**
     * 数量列表（仓库商品）
     */
    private List<WarehouseGoodsCount> counts;

    /**
     * 库存流水类型
     */
    private StockFlowType stockFlowType;

    @Data
    @AllArgsConstructor
    public static class WarehouseGoodsCount{
        /**
         * 仓库ID
         */
        private long warehouseId;

        /**
         * SKU ID
         */
        private long skuId;

        /**
         * 数量
         */
        private int count;
    }
}
