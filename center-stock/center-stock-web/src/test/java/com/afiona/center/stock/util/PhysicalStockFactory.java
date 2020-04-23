package com.afiona.center.stock.util;

import com.afiona.center.stock.constants.enums.StockFlowType;
import com.afiona.center.stock.model.command.PhysicalStockCommand;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * 实物库存工厂
 *
 * @author dengweiyi
 * @date 2020-02-26
 */
public class PhysicalStockFactory {
    public static PhysicalStockCommand createCommand(){
        List<PhysicalStockCommand.WarehouseGoodsCount> counts = Lists.newArrayList();
        counts.add(new PhysicalStockCommand.WarehouseGoodsCount(10000, 10000, 1));
        return new PhysicalStockCommand(counts, null);
    }
}
