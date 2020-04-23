package com.afiona.center.stock.infrastructure.repo.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.afiona.center.stock.domain.aggregate.StockDistribution;
import com.afiona.center.stock.domain.aggregate.PhysicalStockAggregate;
import com.afiona.center.stock.domain.repo.PhysicalStockRepository;
import com.afiona.center.stock.model.query.PhysicalStockQuery;
import com.deepexi.util.pageHelper.PageBean;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PhysicalStockRepositoryIT {
    @Resource
    private PhysicalStockRepository physicalStockRepository;

    @Test
    public void listPage() {
        PhysicalStockQuery query = new PhysicalStockQuery();
        PageBean<StockDistribution> distributions = physicalStockRepository.listPage(query);
        Assert.assertTrue(distributions != null);
    }

    @Test
    public void listByChannelId() {
        List<PhysicalStockAggregate> distributions = physicalStockRepository.listByStoreId(10000L);
        Assert.assertTrue(CollectionUtil.isNotEmpty(distributions));
    }
}