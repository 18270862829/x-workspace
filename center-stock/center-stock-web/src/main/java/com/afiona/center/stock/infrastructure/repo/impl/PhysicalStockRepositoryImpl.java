package com.afiona.center.stock.infrastructure.repo.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.afiona.center.stock.domain.aggregate.StockDistribution;
import com.afiona.center.stock.domain.aggregate.PhysicalStockAggregate;
import com.afiona.center.stock.domain.aggregate.StoreDistribution;
import com.afiona.center.stock.domain.aggregate.WarehouseAggregate;
import com.afiona.center.stock.domain.model.PhysicalStock;
import com.afiona.center.stock.domain.model.Warehouse;
import com.afiona.center.stock.domain.repo.PhysicalStockRepository;
import com.afiona.center.stock.domain.service.aggregate.PhysicalStockAggregateService;
import com.afiona.center.stock.domain.service.aggregate.WarehouseAggregateService;
import com.afiona.center.stock.infrastructure.dao.PhysicalStockDAO;
import com.afiona.center.stock.infrastructure.dao.WarehouseDAO;
import com.afiona.center.stock.infrastructure.dao.WarehouseStoreDAO;
import com.afiona.center.stock.infrastructure.model.PhysicalStockDO;
import com.afiona.center.stock.infrastructure.model.WarehouseDO;
import com.afiona.center.stock.infrastructure.model.WarehouseStoreDO;
import com.afiona.center.stock.model.query.PhysicalStockQuery;
import com.afiona.center.stock.model.query.StoreDistributionDetailsQuery;
import com.afiona.common.pojo.JsonResult;
import com.afiona.common.util.CloneUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.deepexi.util.pageHelper.PageBean;
import com.deepexi.util.pojo.ObjectCloneUtils;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.afiona.center.stock.builder.WarehouseBuilder.toWarehouses;

/**
 * 实物库存repo实现
 *
 * @author dengweiyi
 * @date 2020-02-25
 */
@Repository
public class PhysicalStockRepositoryImpl implements PhysicalStockRepository {
    @Resource
    private PhysicalStockDAO physicalStockDAO;

    @Resource
    private WarehouseStoreDAO warehouseStoreDAO;

    @Resource
    private PhysicalStockAggregateService physicalStockAggregateService;

    @Resource
    private WarehouseDAO warehouseDAO;

    @Resource
    private WarehouseAggregateService warehouseAggregateService;

    @Override
    public PageBean<StockDistribution> listPage(PhysicalStockQuery query) {
        PageHelper.startPage(query.getPage(), query.getSize());
        boolean hasRange = query.getStockRange() != null;
        List<PhysicalStockDO> physicalStockDOList = physicalStockDAO.list(new LambdaQueryWrapper<PhysicalStockDO>()
                .in(CollectionUtil.isNotEmpty(query.getSkuIds()), PhysicalStockDO::getSkuId, query.getSkuIds())
                .between(hasRange, PhysicalStockDO::getAvailable, hasRange ? query.getStockRange().getMin() : null,
                        hasRange ? query.getStockRange().getMax() : null));
        // TODO 没考虑仓库/门店的模糊查询
        List<PhysicalStockAggregate> physicalStockAggregates = ObjectCloneUtils.convertList(physicalStockDOList, PhysicalStockAggregate.class);
        physicalStockAggregateService.aggregate(physicalStockAggregates);
        Map<Long, List<PhysicalStockAggregate>> stockMap = physicalStockAggregates.stream().collect(Collectors.groupingBy(PhysicalStockAggregate::getSkuId));
        List<StockDistribution> distributions = Lists.newArrayList();
        for(Map.Entry<Long, List<PhysicalStockAggregate>> entry : stockMap.entrySet()){
            StockDistribution distribution = new StockDistribution();
            distribution.setSkuId(entry.getKey());
            distribution.setStocks(entry.getValue());
            distributions.add(distribution);
        }

        // FIXME 这里有bug，后面来改
        PageBean<PhysicalStockDO> oldPageBean = new PageBean<>(physicalStockDOList);
        PageBean<StockDistribution> pageBean = CloneUtil.clone(oldPageBean, PageBean.class);
        pageBean.setContent(distributions);
        return pageBean;
    }

    @Override
    public List<PhysicalStockAggregate> listByStoreId(long storeId) {
        List<WarehouseStoreDO> warehouseStoreDOList = warehouseStoreDAO.list(new LambdaQueryWrapper<WarehouseStoreDO>()
                .eq(WarehouseStoreDO::getStoreId, storeId));
        Set<Long> warehouseIdSet = warehouseStoreDOList.stream().map(WarehouseStoreDO::getWarehouseId).collect(Collectors.toSet());
        List<PhysicalStockDO> physicalStockDOList = physicalStockDAO.list(new LambdaQueryWrapper<PhysicalStockDO>()
                .in(PhysicalStockDO::getWarehouseId, Lists.newArrayList(warehouseIdSet)));
        List<PhysicalStockAggregate> physicalStockAggregates = ObjectCloneUtils.convertList(physicalStockDOList, PhysicalStockAggregate.class);
        physicalStockAggregateService.aggregate(physicalStockAggregates);
        return physicalStockAggregates;
    }

    @Override
    public JsonResult<List<StoreDistribution>> listStoreDistribution(List<Long> storeIds) {
        List<WarehouseStoreDO> allWarehouseStoreDOS = warehouseStoreDAO.list(new LambdaQueryWrapper<WarehouseStoreDO>()
                .in(CollectionUtil.isNotEmpty(storeIds), WarehouseStoreDO::getStoreId, storeIds));

        if(CollectionUtil.isEmpty(allWarehouseStoreDOS)){
            return JsonResult.create(Lists.newArrayList());
        }
        Map<Long, List<WarehouseStoreDO>> warehouseStoreMap = allWarehouseStoreDOS.stream()
                .collect(Collectors.groupingBy(WarehouseStoreDO::getStoreId));

        Set<Long> warehouseIdSet = allWarehouseStoreDOS.stream().map(WarehouseStoreDO::getWarehouseId).collect(Collectors.toSet());
        List<PhysicalStockDO> physicalStockDOList = physicalStockDAO.list(new LambdaQueryWrapper<PhysicalStockDO>()
                .in(PhysicalStockDO::getWarehouseId, Lists.newArrayList(warehouseIdSet)));
        List<PhysicalStock> allStocks = ObjectCloneUtils.convertList(physicalStockDOList, PhysicalStock.class);
        Map<Long, List<PhysicalStock>> stockMap = allStocks.stream()
                .collect(Collectors.groupingBy(PhysicalStock::getWarehouseId));

        List<StoreDistribution> storeDistributions = Lists.newArrayList();
        for(long storeId : storeIds){
            List<PhysicalStock> storeStocks = Lists.newArrayList();

            List<WarehouseStoreDO> warehouseStoreDOS = warehouseStoreMap.get(storeId);
            if(CollectionUtil.isNotEmpty(warehouseStoreDOS)){
                for(WarehouseStoreDO warehouseStoreDO : warehouseStoreDOS){
                    List<PhysicalStock> stocks = stockMap.get(warehouseStoreDO.getWarehouseId());
                    if(CollectionUtil.isNotEmpty(stocks)){
                        storeStocks.addAll(stocks);
                    }
                }
            }

            StoreDistribution storeDistribution = new StoreDistribution();
            storeDistribution.setStoreId(storeId);
            storeDistribution.setStocks(storeStocks);
            storeDistributions.add(storeDistribution);
        }
        return JsonResult.create(storeDistributions);
    }

    @Override
    public JsonResult<PageBean<WarehouseAggregate>> storeDistributionDetails(StoreDistributionDetailsQuery query) {
        List<WarehouseStoreDO> warehouseStoreDOS = warehouseStoreDAO.list(new LambdaQueryWrapper<WarehouseStoreDO>()
                .eq(WarehouseStoreDO::getStoreId, query.getStoreId()));
        if(CollectionUtil.isEmpty(warehouseStoreDOS)){
            return JsonResult.create(new PageBean<>());
        }
        List<Long> warehouseIds = warehouseStoreDOS.stream().map(WarehouseStoreDO::getWarehouseId).collect(Collectors.toList());
        PageHelper.startPage(query.getPage(), query.getSize());
        List<WarehouseDO> warehouseDOS = warehouseDAO.list(new LambdaQueryWrapper<WarehouseDO>()
                .in(WarehouseDO::getId, warehouseIds));

        List<Warehouse> warehouses = toWarehouses(warehouseDOS);
        List<WarehouseAggregate> warehouseAggregates = ObjectCloneUtils.convertList(warehouses, WarehouseAggregate.class);
        warehouseAggregateService.aggregate(warehouseAggregates);

        PageBean<WarehouseAggregate> pageBean = CloneUtil.clone(new PageBean<>(warehouseDOS), PageBean.class);
        pageBean.setContent(warehouseAggregates);
        return JsonResult.create(pageBean);
    }
}
