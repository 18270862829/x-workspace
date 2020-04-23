package com.afiona.center.stock.infrastructure.repo.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.afiona.center.stock.domain.aggregate.StockFlowAggregate;
import com.afiona.center.stock.domain.repo.StockFlowRepository;
import com.afiona.center.stock.model.query.StockFlowQuery;
import com.deepexi.util.pageHelper.PageBean;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockFlowRepositoryIT {
    @Resource
    private StockFlowRepository stockFlowRepository;

    @Test
    public void listPage() {
        StockFlowQuery query = new StockFlowQuery();
        PageBean<StockFlowAggregate> pageBean = stockFlowRepository.listPage(query);
        Assert.assertTrue(pageBean != null);
    }
}