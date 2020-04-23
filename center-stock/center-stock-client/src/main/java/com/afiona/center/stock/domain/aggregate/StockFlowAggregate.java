package com.afiona.center.stock.domain.aggregate;

import com.afiona.center.stock.domain.model.StockFlow;
import com.afiona.center.stock.domain.model.Warehouse;
import lombok.Data;

/**
 * 库存流水聚合根
 *
 * @author dengweiyi
 * @date 2020-02-27
 */
@Data
public class StockFlowAggregate extends StockFlow {
    /**
     * 仓库
     */
    private Warehouse warehouse;
}
