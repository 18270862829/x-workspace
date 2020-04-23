package com.afiona.center.stock.domain.service.aggregate;

import com.afiona.center.stock.domain.aggregate.PhysicalStockAggregate;
import com.afiona.center.stock.domain.model.Warehouse;
import com.afiona.center.stock.infrastructure.dao.WarehouseDAO;
import com.afiona.center.stock.infrastructure.model.WarehouseDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.deepexi.util.pojo.ObjectCloneUtils;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 实物库存聚合器
 *
 * @author dengweiyi
 * @date 2020-02-25
 */
@Service
public class PhysicalStockAggregateService {
    @Resource
    private WarehouseDAO warehouseDAO;

    public void aggregate(List<PhysicalStockAggregate> physicalStockAggregates){
        Set<Long> warehouseIdSet = physicalStockAggregates.stream().map(PhysicalStockAggregate::getWarehouseId).collect(Collectors.toSet());
        List<WarehouseDO> warehouseDOList = warehouseDAO.list(new LambdaQueryWrapper<WarehouseDO>()
                .in(WarehouseDO::getId, Lists.newArrayList(warehouseIdSet)));
        List<Warehouse> warehouses = ObjectCloneUtils.convertList(warehouseDOList, Warehouse.class);
        Map<Long, Warehouse> warehouseMap = warehouses.stream().collect(Collectors.toMap(Warehouse::getId, Function.identity()));
        for(PhysicalStockAggregate physicalStockAggregate : physicalStockAggregates){
            Warehouse warehouse = warehouseMap.get(physicalStockAggregate.getWarehouseId());
            if(warehouse == null){
                continue;
            }
            physicalStockAggregate.setWarehouse(warehouse);
        }
    }
}
