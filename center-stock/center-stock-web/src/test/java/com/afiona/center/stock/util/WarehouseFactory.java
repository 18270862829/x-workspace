package com.afiona.center.stock.util;

import com.afiona.center.stock.constants.enums.WarehouseStatus;
import com.afiona.center.stock.constants.enums.WarehouseType;
import com.afiona.center.stock.domain.aggregate.WarehouseAggregate;
import com.afiona.center.stock.domain.model.Warehouse;
import org.assertj.core.util.Lists;

import java.util.List;

/**
 * 仓库工厂
 *
 * @author dengweiyi
 * @date 2020-02-26
 */
public class WarehouseFactory {
    public static WarehouseAggregate create(){
        WarehouseAggregate warehouseAggregate = new WarehouseAggregate();
        warehouseAggregate.setType(WarehouseType.REAL);
        warehouseAggregate.setName("测试仓库");
        warehouseAggregate.setCode("XXXXXX");

        Warehouse.Position position = new Warehouse.Position(45.0, 45.0);
        Warehouse.Regin regin = new Warehouse.Regin("四川省", "成都市", "高新区");
        Warehouse.Address address = new Warehouse.Address(regin, "香年广场", position);
        warehouseAggregate.setAddress(address);

        warehouseAggregate.setContactor("小邓");
        warehouseAggregate.setContactNumber("1234567890");
        warehouseAggregate.setStatus(WarehouseStatus.TURN_ON);

        List<Long> storeIds = Lists.newArrayList(10000L);
        warehouseAggregate.setStoreIds(storeIds);
        return warehouseAggregate;
    }

}
