package com.afiona.center.stock.domain.service.aggregate;

import com.afiona.center.stock.domain.aggregate.StockFlowAggregate;
import com.afiona.center.stock.domain.model.Warehouse;
import com.afiona.center.stock.infrastructure.dao.WarehouseDAO;
import com.afiona.center.stock.infrastructure.model.WarehouseDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.deepexi.util.pojo.ObjectCloneUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 库存流水聚合器
 *
 * @author dengweiyi
 * @date 2020-02-27
 */
@Service
public class StockFlowAggregateService {
    @Resource
    private WarehouseDAO warehouseDAO;

    public void aggregate(List<StockFlowAggregate> stockFlowAggregates){
        List<Long> warehouseIds = stockFlowAggregates.stream().map(StockFlowAggregate::getWarehouseId).collect(Collectors.toList());
        List<WarehouseDO> warehouseDOList = warehouseDAO.list(new LambdaQueryWrapper<WarehouseDO>().in(WarehouseDO::getId, warehouseIds));
        List<Warehouse> warehouses = ObjectCloneUtils.convertList(warehouseDOList, Warehouse.class);
        Map<Long, Warehouse> warehouseMap = warehouses.stream().collect(Collectors.toMap(Warehouse::getId, Function.identity()));
        for(StockFlowAggregate stockFlowAggregate : stockFlowAggregates){
            Warehouse warehouse = warehouseMap.get(stockFlowAggregate.getWarehouseId());
            if(warehouse == null){
                continue;
            }
            stockFlowAggregate.setWarehouse(warehouse);
        }
    }
}
