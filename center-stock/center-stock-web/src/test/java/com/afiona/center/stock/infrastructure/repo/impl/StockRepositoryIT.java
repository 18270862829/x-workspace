package com.afiona.center.stock.infrastructure.repo.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.afiona.center.stock.domain.model.Stock;
import com.afiona.center.stock.domain.repo.StockRepository;
import com.afiona.center.stock.model.query.StockPageQuery;
import com.afiona.center.stock.model.query.StockQuery;
import com.deepexi.util.pageHelper.PageBean;
import org.assertj.core.util.Lists;
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
public class StockRepositoryIT {
    @Resource
    private StockRepository stockRepository;

    @Test
    public void listPage() {
        StockPageQuery query = new StockPageQuery();
        PageBean<Stock> pageBean = stockRepository.listPage(query);
        Assert.assertTrue(pageBean != null);
    }

    @Test
    public void listStock() {
        StockQuery query = new StockQuery();
        query.setSkuIds(Lists.newArrayList(10000L));
        List<Stock> stocks = stockRepository.listStock(query);
        Assert.assertTrue(CollectionUtil.isNotEmpty(stocks));
    }
}