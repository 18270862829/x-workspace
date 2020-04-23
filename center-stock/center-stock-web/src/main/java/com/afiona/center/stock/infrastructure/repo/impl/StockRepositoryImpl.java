package com.afiona.center.stock.infrastructure.repo.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.afiona.center.stock.domain.model.Stock;
import com.afiona.center.stock.infrastructure.model.StockDO;
import com.afiona.center.stock.model.query.StockPageQuery;
import com.afiona.center.stock.model.query.StockQuery;
import com.afiona.center.stock.domain.repo.StockRepository;
import com.afiona.center.stock.infrastructure.dao.StockDAO;
import com.afiona.common.util.CloneUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.deepexi.util.pageHelper.PageBean;
import com.deepexi.util.pojo.ObjectCloneUtils;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 销售库存repo实现
 *
 * @author dengweiyi
 * @date 2019-09-29
 */

@Repository
public class StockRepositoryImpl implements StockRepository {
    @Resource
    private StockDAO stockDAO;

    @Override
    public PageBean<Stock> listPage(StockPageQuery query) {
        PageHelper.startPage(query.getPage(), query.getSize());
        boolean notEmpty = CollectionUtil.isNotEmpty(query.getStoreIds());
        List<StockDO> stockDOList = stockDAO.list(new LambdaQueryWrapper<StockDO>().in(notEmpty, StockDO::getStoreId, query.getStoreIds()));
        List<Stock> stocks = ObjectCloneUtils.convertList(stockDOList, Stock.class);

        PageBean<StockDO> oldPageBean = new PageBean<>(stockDOList);
        PageBean<Stock> pageBean = CloneUtil.clone(oldPageBean, PageBean.class);
        pageBean.setContent(stocks);
        return pageBean;
    }

    @Override
    public List<Stock> listStock(StockQuery query) {
        if(CollectionUtil.isEmpty(query.getSkuIds())){
            return Lists.newArrayList();
        }
        List<StockDO> stockDOList = stockDAO.list(new LambdaQueryWrapper<StockDO>()
                .eq(query.getStoreId() != null, StockDO::getStoreId, query.getStoreId())
                .in(CollUtil.isNotEmpty(query.getSkuIds()), StockDO::getSkuId, query.getSkuIds()));
        List<Stock> stocks = ObjectCloneUtils.convertList(stockDOList, Stock.class);
        return stocks;
    }
}
