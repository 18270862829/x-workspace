package com.afiona.center.stock.domain.service.aggregate;

import com.afiona.center.stock.domain.aggregate.WarehouseAggregate;
import com.afiona.center.stock.domain.model.PhysicalStock;
import com.afiona.center.stock.infrastructure.dao.PhysicalStockDAO;
import com.afiona.center.stock.infrastructure.dao.WarehouseStoreDAO;
import com.afiona.center.stock.infrastructure.model.PhysicalStockDO;
import com.afiona.center.stock.infrastructure.model.WarehouseStoreDO;
import com.afiona.common.util.CloneUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import cn.hutool.core.collection.CollectionUtil;

/**
 * 仓库聚合器
 *
 * @author dengweiyi
 * @date 2020-02-25
 */
@Service
public class WarehouseAggregateService {
    @Resource
    private WarehouseStoreDAO warehouseStoreDAO;

    @Resource
    private PhysicalStockDAO physicalStockDAO;

    public void aggregate(List<WarehouseAggregate> warehouseAggregates) {
        if (CollectionUtil.isEmpty(warehouseAggregates)) {
            return;
        }
        List<Long> warehouseIds = warehouseAggregates.stream().map(WarehouseAggregate::getId).collect(Collectors.toList());
        aggregateStores(warehouseAggregates, warehouseIds);
        aggregateStocks(warehouseAggregates, warehouseIds);
    }

    private void aggregateStores(List<WarehouseAggregate> warehouseAggregates, List<Long> warehouseIds) {
        List<WarehouseStoreDO> allWarehouseStoreDOList = warehouseStoreDAO.list(new LambdaQueryWrapper<WarehouseStoreDO>()
                .in(WarehouseStoreDO::getWarehouseId, warehouseIds));
        Map<Long, List<WarehouseStoreDO>> warehouseChannelDOMap = allWarehouseStoreDOList.stream()
                .collect(Collectors.groupingBy(WarehouseStoreDO::getWarehouseId));

        for (WarehouseAggregate warehouseAggregate : warehouseAggregates) {
            List<WarehouseStoreDO> warehouseStoreDOList = warehouseChannelDOMap.get(warehouseAggregate.getId());
            if (CollectionUtil.isEmpty(warehouseStoreDOList)) {
                continue;
            }
            List<Long> storeIds = warehouseStoreDOList.stream().map(WarehouseStoreDO::getStoreId).collect(Collectors.toList());
            warehouseAggregate.setStoreIds(storeIds);
        }
    }

    private void aggregateStocks(List<WarehouseAggregate> warehouseAggregates, List<Long> warehouseIds) {
        List<PhysicalStockDO> physicalStockDOS = physicalStockDAO.list(new LambdaQueryWrapper<PhysicalStockDO>()
                .in(PhysicalStockDO::getWarehouseId, warehouseIds));
        List<PhysicalStock> physicalStocks = CloneUtil.cloneList(physicalStockDOS, PhysicalStock.class);
        Map<Long, List<PhysicalStock>> stockMap = physicalStocks.stream().collect(Collectors.groupingBy(PhysicalStock::getWarehouseId));

        for (WarehouseAggregate warehouseAggregate : warehouseAggregates) {
            List<PhysicalStock> stocks = stockMap.get(warehouseAggregate.getId());
            if (CollectionUtil.isEmpty(stocks)) {
                continue;
            }
            warehouseAggregate.setStocks(stocks);
        }
    }

    public List<PhysicalStock> list(List<Long> warehouseIds) {
        List<PhysicalStockDO> physicalStockDOS = physicalStockDAO.list(new LambdaQueryWrapper<PhysicalStockDO>()
                .in(PhysicalStockDO::getWarehouseId, warehouseIds));
        return CloneUtil.cloneList(physicalStockDOS, PhysicalStock.class);
    }
}
