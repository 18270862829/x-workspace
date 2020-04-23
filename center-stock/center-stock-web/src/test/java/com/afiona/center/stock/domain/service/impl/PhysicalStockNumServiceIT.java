package com.afiona.center.stock.domain.service.impl;

import com.afiona.center.stock.constants.enums.StockFlowType;
import com.afiona.center.stock.domain.service.PhysicalStockNumService;
import com.afiona.center.stock.model.command.PhysicalStockCommand;
import com.afiona.center.stock.util.PhysicalStockFactory;
import com.afiona.common.pojo.JsonResult;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PhysicalStockNumServiceIT {
    @Resource
    private PhysicalStockNumService physicalStockNumService;

    @Test
    public void freeze() {
        PhysicalStockCommand command = PhysicalStockFactory.createCommand();
        JsonResult<Void> result = physicalStockNumService.freeze(command);
        Assert.assertTrue(result.success());
    }

    @Test
    public void unfreeze() {
        PhysicalStockCommand command = PhysicalStockFactory.createCommand();
        JsonResult<Void> result = physicalStockNumService.unfreeze(command);
        Assert.assertTrue(result.success());
    }

    @Test
    public void reduce() {
        PhysicalStockCommand command = PhysicalStockFactory.createCommand();
        JsonResult<Void> result = physicalStockNumService.reduce(command, true);
        Assert.assertTrue(result.success());
    }

    @Test
    public void increase() {
        PhysicalStockCommand command = PhysicalStockFactory.createCommand();
        command.setStockFlowType(StockFlowType.PURCHASE_IN);
        JsonResult<Void> result = physicalStockNumService.increase(command, true);
        Assert.assertTrue(result.success());
    }
}