package com.afiona.center.stock.infrastructure.repo.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.afiona.center.stock.domain.aggregate.StoreWarehouses;
import com.afiona.center.stock.domain.aggregate.WarehouseAggregate;
import com.afiona.center.stock.domain.repo.WarehouseRepository;
import com.afiona.center.stock.model.query.WarehouseQuery;
import com.afiona.center.stock.util.WarehouseFactory;
import com.afiona.common.pojo.JsonResult;
import com.deepexi.util.pageHelper.PageBean;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WarehouseRepositoryIT {
    @Resource
    private WarehouseRepository warehouseRepository;

    @Test
    public void listPage() {
        WarehouseQuery query = new WarehouseQuery();
        PageBean<WarehouseAggregate> pageBean = warehouseRepository.listPage(query);
        Assert.assertTrue(pageBean != null);
    }

    @Test
    public void getById() {
        WarehouseAggregate warehouseAggregate = warehouseRepository.getById(10000L);
        Assert.assertTrue(warehouseAggregate != null);
    }

    @Test
    public void store() {
        WarehouseAggregate warehouseAggregate = WarehouseFactory.create();
        JsonResult<Void> result = warehouseRepository.store(warehouseAggregate);
        Assert.assertTrue(result.success());
    }

    @Test
    public void switchStatus() {
        JsonResult<Void> result = warehouseRepository.switchStatus(10000);
        Assert.assertTrue(result.success());
    }

    @Test
    public void listByIds(){
        List<WarehouseAggregate> warehouseAggregates = warehouseRepository.listByIds(Lists.newArrayList(10000L));
        Assert.assertTrue(CollectionUtil.isNotEmpty(warehouseAggregates));
    }

    @Test
    public void groupByStore(){
        List<StoreWarehouses> storeWarehousesList = warehouseRepository.groupByStore(Lists.newArrayList(0L));
        Assert.assertTrue(CollectionUtil.isNotEmpty(storeWarehousesList));
    }
}