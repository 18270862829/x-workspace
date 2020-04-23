package com.afiona.center.stock.domain.repo;

import com.afiona.center.stock.domain.model.Stock;
import com.afiona.center.stock.model.query.StockPageQuery;
import com.afiona.center.stock.model.query.StockQuery;
import com.deepexi.util.pageHelper.PageBean;

import java.util.List;

/**
 * 销售库存repo
 *
 * @author dengweiyi
 * @date 2019-09-29
 */
public interface StockRepository {
    /**
     * 分页查询（按渠道分组）
     *
     * @param query
     * @return : java.util.List<com.afiona.center.stock.domain.model.Stock>
     */
    PageBean<Stock> listPage(StockPageQuery query);

    /**
     * 列表查询
     *
     * @param query
     * @return : java.util.List<Stock>
     */
    List<Stock> listStock(StockQuery query);
}
