package com.afiona.center.stock.builder;

import cn.hutool.core.collection.CollectionUtil;
import com.afiona.center.stock.domain.model.Warehouse;
import com.afiona.center.stock.infrastructure.model.WarehouseDO;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * 仓库构建器
 *
 * @author dengweiyi
 * @date 2020-02-25
 */
public class WarehouseBuilder {
    public static List<Warehouse> toWarehouses(List<WarehouseDO> warehouseDOList){
        if(CollectionUtil.isEmpty(warehouseDOList)){
            return Lists.newArrayList();
        }
        List<Warehouse> list = Lists.newArrayList();
        for(WarehouseDO warehouseDO : warehouseDOList){
            list.add(toWarehouse(warehouseDO));
        }
        return list;
    }

    private static Warehouse toWarehouse(WarehouseDO warehouseDO){
        Warehouse warehouse = warehouseDO.clone(Warehouse.class);
        warehouse.setAddress(toAddress(warehouseDO));
        return warehouse;
    }

    private static Warehouse.Address toAddress(WarehouseDO warehouseDO){
        return new Warehouse.Address(toRegin(warehouseDO), warehouseDO.getDetailedAddress(), toPosition(warehouseDO));
    }

    private static Warehouse.Regin toRegin(WarehouseDO warehouseDO){
        return new Warehouse.Regin(warehouseDO.getProvince(), warehouseDO.getCity(), warehouseDO.getArea());
    }

    private static Warehouse.Position toPosition(WarehouseDO warehouseDO){
        return new Warehouse.Position(warehouseDO.getLongitude(), warehouseDO.getLatitude());
    }

    public static List<WarehouseDO> toDOList(List<Warehouse> warehouses){
        if(CollectionUtil.isEmpty(warehouses)){
            return Lists.newArrayList();
        }
        List<WarehouseDO> warehouseDOList = Lists.newArrayList();
        for(Warehouse warehouse : warehouses){
            warehouseDOList.add(toDO(warehouse));
        }
        return warehouseDOList;
    }

    public static WarehouseDO toDO(Warehouse warehouse){
        WarehouseDO warehouseDO = warehouse.clone(WarehouseDO.class);
        warehouseDO.setProvince(warehouse.getAddress().getRegin().getProvince());
        warehouseDO.setCity(warehouse.getAddress().getRegin().getCity());
        warehouseDO.setArea(warehouse.getAddress().getRegin().getArea());

        warehouseDO.setDetailedAddress(warehouse.getAddress().getDetailedAddress());

        warehouseDO.setLongitude(warehouse.getAddress().getPosition().getLongitude());
        warehouseDO.setLatitude(warehouse.getAddress().getPosition().getLatitude());
        return warehouseDO;
    }
}
