package com.afiona.center.stock.domain.repo;

import com.afiona.center.stock.domain.aggregate.StockFlowAggregate;
import com.afiona.center.stock.domain.model.StockFlow;
import com.afiona.center.stock.model.query.StockFlowQuery;
import com.deepexi.util.pageHelper.PageBean;

import java.util.List;

/**
 * 库存流水repo
 *
 * @author dengweiyi
 * @date 2020-02-27
 */
public interface StockFlowRepository {
    /**
     * 分页查询
     *
     * @param query
     * @return : java.util.List<com.afiona.center.stock.domain.aggregate.StockFlowAggregate>
     */
    PageBean<StockFlowAggregate> listPage(StockFlowQuery query);
}
