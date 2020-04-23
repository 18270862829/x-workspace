package com.afiona.center.stock.infrastructure.repo.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.afiona.center.stock.constants.enums.WarehouseStatus;
import com.afiona.center.stock.constants.enums.result.StockResultEnum;
import com.afiona.center.stock.domain.aggregate.StoreWarehouses;
import com.afiona.center.stock.domain.aggregate.WarehouseAggregate;
import com.afiona.center.stock.domain.model.PhysicalStock;
import com.afiona.center.stock.domain.model.Warehouse;
import com.afiona.center.stock.domain.repo.WarehouseRepository;
import com.afiona.center.stock.domain.service.aggregate.WarehouseAggregateService;
import com.afiona.center.stock.infrastructure.dao.WarehouseStoreDAO;
import com.afiona.center.stock.infrastructure.dao.WarehouseDAO;
import com.afiona.center.stock.infrastructure.model.WarehouseStoreDO;
import com.afiona.center.stock.infrastructure.model.WarehouseDO;
import com.afiona.center.stock.model.query.WarehouseQuery;
import com.afiona.common.pojo.JsonResult;
import com.afiona.common.util.CloneUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.deepexi.util.pageHelper.PageBean;
import com.deepexi.util.pojo.ObjectCloneUtils;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.afiona.center.stock.builder.WarehouseBuilder.*;
import static com.afiona.center.stock.util.PositionHelper.*;

/**
 * 仓库repo实现
 *
 * @author dengweiyi
 * @date 2020-02-25
 */
@Repository
public class WarehouseRepositoryImpl implements WarehouseRepository {
    @Resource
    private WarehouseDAO warehouseDAO;

    @Resource
    private WarehouseAggregateService warehouseAggregateService;

    @Resource
    private WarehouseStoreDAO warehouseStoreDAO;

    @Override
    public PageBean<WarehouseAggregate> listPage(WarehouseQuery query) {
        PageHelper.startPage(query.getPage(), query.getSize());
        List<WarehouseDO> warehouseDOList = warehouseDAO.list(new LambdaQueryWrapper<WarehouseDO>()
                .eq(!Strings.isNullOrEmpty(query.getName()), WarehouseDO::getName, query.getName())
                .eq(query.getStatus() != null, WarehouseDO::getStatus, query.getStatus())
                .eq(query.getType() != null, WarehouseDO::getType, query.getType())
                .orderByDesc(WarehouseDO::getCreatedTime));
        List<Warehouse> warehouses = toWarehouses(warehouseDOList);
        List<WarehouseAggregate> warehouseAggregates = ObjectCloneUtils.convertList(warehouses, WarehouseAggregate.class);
        warehouseAggregateService.aggregate(warehouseAggregates);

        PageBean<WarehouseDO> oldPageBean = new PageBean<>(warehouseDOList);
        PageBean<WarehouseAggregate> pageBean = CloneUtil.clone(oldPageBean, PageBean.class);
        pageBean.setContent(warehouseAggregates);
        return pageBean;
    }

    @Override
    public WarehouseAggregate getById(long warehouseId) {
        List<WarehouseAggregate> warehouseAggregates = listByIds(Lists.newArrayList(warehouseId));
        if(CollectionUtil.isEmpty(warehouseAggregates)){
            return null;
        }
        return warehouseAggregates.get(0);
    }

    @Override
    public List<WarehouseAggregate> listByIds(List<Long> warehouseIds) {
        if(CollectionUtil.isEmpty(warehouseIds)){
            return Lists.newArrayList();
        }
        List<WarehouseDO> warehouseDOS = warehouseDAO.list(new LambdaQueryWrapper<WarehouseDO>().in(WarehouseDO::getId, warehouseIds));
        List<WarehouseAggregate> warehouseAggregates = CloneUtil.cloneList(warehouseDOS, WarehouseAggregate.class);
        warehouseAggregateService.aggregate(warehouseAggregates);
        return warehouseAggregates;
    }

    @Override
    public JsonResult<Void> store(WarehouseAggregate warehouseAggregate) {
        if(Strings.isNullOrEmpty(warehouseAggregate.getName())){
            return JsonResult.create(StockResultEnum.NO_NAME);
        }
        if(warehouseAggregate.getAddress() == null
                || warehouseAggregate.getAddress().getRegin() == null
                || warehouseAggregate.getAddress().getPosition() == null){
            return JsonResult.create(StockResultEnum.EXCEPTION);
        }
        // TODO 省市区校验
        if(Strings.isNullOrEmpty(warehouseAggregate.getAddress().getDetailedAddress())){
            return JsonResult.create(StockResultEnum.NO_DETAIL_ADDRESS);
        }
        if(!isPositionCorrect(warehouseAggregate.getAddress().getPosition())){
            return JsonResult.create(StockResultEnum.WRONG_POSITION);
        }
        List<Long> storeIds = warehouseAggregate.getStoreIds() == null ? Lists.newArrayList() : warehouseAggregate.getStoreIds();
        long notRepeatCount = storeIds.stream().distinct().count();
        if(storeIds.size() > notRepeatCount){
            return JsonResult.create(StockResultEnum.CHANNEL_REPEAT);
        }
        WarehouseDO warehouseDO = toDO(warehouseAggregate);
        warehouseDAO.saveOrUpdate(warehouseDO);

        warehouseStoreDAO.remove(new LambdaQueryWrapper<WarehouseStoreDO>().eq(WarehouseStoreDO::getWarehouseId, warehouseAggregate.getId()));
        if(CollectionUtil.isNotEmpty(storeIds)){
            List<WarehouseStoreDO> warehouseStoreDOList = Lists.newArrayList();
            storeIds.stream().forEach(storeId -> {
                WarehouseStoreDO warehouseStoreDO = new WarehouseStoreDO();
                warehouseStoreDO.setWarehouseId(warehouseDO.getId());
                warehouseStoreDO.setStoreId(storeId);
                warehouseStoreDOList.add(warehouseStoreDO);
            });
            warehouseStoreDAO.saveBatch(warehouseStoreDOList);
        }
        return JsonResult.create();
    }

    @Override
    public JsonResult<Void> switchStatus(long id) {
        WarehouseDO warehouseDO = warehouseDAO.getById(id);
        if(warehouseDO == null){
            return JsonResult.create(StockResultEnum.NOT_EXIST);
        }
        //如果是到禁用，则判断是否有库存
        if (warehouseDO.getStatus() == WarehouseStatus.TURN_ON) {
            List<PhysicalStock> list = warehouseAggregateService.list(Lists.newArrayList(warehouseDO.getId()));
            if (CollectionUtil.isNotEmpty(list)) {
                int sum = list.stream().filter(Objects::nonNull).mapToInt(PhysicalStock::getAvailable).sum();
                if (sum > 0) {
                    return JsonResult.create(StockResultEnum.INVENTORY_EXISTS);
                }
            }
        }
        WarehouseStatus targetStatus = warehouseDO.getStatus() == WarehouseStatus.TURN_OFF ? WarehouseStatus.TURN_ON : WarehouseStatus.TURN_OFF;
        warehouseDO.setStatus(targetStatus);
        warehouseDAO.updateById(warehouseDO);
        return JsonResult.create();
    }

    @Override
    public List<StoreWarehouses> groupByStore(List<Long> storeIds) {
        if(CollectionUtil.isEmpty(storeIds)){
            return Lists.newArrayList();
        }
        List<WarehouseStoreDO> allWarehouseStoreDOS = warehouseStoreDAO.list(new LambdaQueryWrapper<WarehouseStoreDO>()
                .in(WarehouseStoreDO::getStoreId, storeIds));
        Map<Long, List<WarehouseStoreDO>> warehouseStoreMap = allWarehouseStoreDOS.stream().collect(Collectors.groupingBy(WarehouseStoreDO::getStoreId));

        Set<Long> warehouseIds = allWarehouseStoreDOS.stream().map(WarehouseStoreDO::getWarehouseId).collect(Collectors.toSet());
        List<WarehouseDO> warehouseDOS = warehouseDAO.list(new LambdaQueryWrapper<WarehouseDO>()
                .in(WarehouseDO::getId, Lists.newArrayList(warehouseIds)));
        List<Warehouse> allWarehouses = CloneUtil.cloneList(warehouseDOS, Warehouse.class);
        Map<Long, Warehouse> warehouseMap = allWarehouses.stream().collect(Collectors.toMap(Warehouse::getId, Function.identity()));

        List<StoreWarehouses> storeWarehousesList = Lists.newArrayList();
        for(Map.Entry<Long, List<WarehouseStoreDO>> entry : warehouseStoreMap.entrySet()){
            long storeId = entry.getKey();
            List<Warehouse> warehouses = Lists.newArrayList();

            List<WarehouseStoreDO> warehouseStoreDOS = entry.getValue();
            for(WarehouseStoreDO warehouseStoreDO : warehouseStoreDOS){
                Warehouse warehouse = warehouseMap.get(warehouseStoreDO.getWarehouseId());
                if(warehouse == null){
                    continue;
                }
                warehouses.add(warehouse);
            }

            StoreWarehouses storeWarehouses = new StoreWarehouses();
            storeWarehouses.setStoreId(storeId);
            storeWarehouses.setWarehouses(warehouses);
            storeWarehousesList.add(storeWarehouses);
        }
        return storeWarehousesList;
    }
}
