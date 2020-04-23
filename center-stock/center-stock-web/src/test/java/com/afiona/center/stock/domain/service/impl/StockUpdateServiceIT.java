package com.afiona.center.stock.domain.service.impl;

import com.afiona.center.stock.domain.service.StockUpdateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockUpdateServiceIT {
    @Resource
    private StockUpdateService stockUpdateService;

    @Test
    public void update() {
        stockUpdateService.update();
    }
}