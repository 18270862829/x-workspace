package com.afiona.center.stock.domain.service.impl;

import com.afiona.center.stock.domain.service.StockNumService;
import com.afiona.center.stock.model.command.StockCommand;
import com.afiona.center.stock.util.StockFactory;
import com.afiona.common.pojo.JsonResult;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockNumServiceIT {
    @Resource
    private StockNumService stockNumService;

    @Test
    public void freeze() {
        StockCommand command = StockFactory.createCommand();
        JsonResult<Void> result = stockNumService.freeze(command);
        Assert.assertTrue(result.success());
    }

    @Test
    public void unfreeze() {
        StockCommand command = StockFactory.createCommand();
        JsonResult<Void> result = stockNumService.unfreeze(command);
        Assert.assertTrue(result.success());
    }

    @Test
    public void reduce() {
        StockCommand command = StockFactory.createCommand();
        JsonResult<Void> result = stockNumService.reduce(command, false);
        Assert.assertTrue(result.success());
    }

    @Test
    public void increase() {
        StockCommand command = StockFactory.createCommand();
        JsonResult<Void> result = stockNumService.increase(command, true);
        Assert.assertTrue(result.success());
    }
}