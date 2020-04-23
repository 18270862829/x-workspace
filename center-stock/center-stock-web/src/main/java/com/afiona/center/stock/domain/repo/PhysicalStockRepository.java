package com.afiona.center.stock.domain.repo;

import com.afiona.center.stock.domain.aggregate.StockDistribution;
import com.afiona.center.stock.domain.aggregate.PhysicalStockAggregate;
import com.afiona.center.stock.domain.aggregate.StoreDistribution;
import com.afiona.center.stock.domain.aggregate.WarehouseAggregate;
import com.afiona.center.stock.model.query.PhysicalStockQuery;
import com.afiona.center.stock.model.query.StoreDistributionDetailsQuery;
import com.afiona.common.pojo.JsonResult;
import com.deepexi.util.pageHelper.PageBean;

import java.util.List;

/**
 * 实物库存repo
 *
 * @author dengweiyi
 * @date 2020-02-25
 */
public interface PhysicalStockRepository {
    /**
     * 分页查询（按SKU ID分组）
     *
     * @param query
     * @return : java.util.List<com.afiona.center.stock.domain.aggregate.StockDistribution>
     */
    PageBean<StockDistribution> listPage(PhysicalStockQuery query);

    /**
     * 查询库存分布列表（根据渠道ID）
     *
     * @param storeId
     * @return : java.util.List<com.afiona.center.stock.domain.aggregate.PhysicalStockAggregate>
     */
    List<PhysicalStockAggregate> listByStoreId(long storeId);

    /**
     * 列表查询店铺库存分布
     *
     * @param storeIds
     * @return : com.afiona.common.pojo.JsonResult<java.util.List<com.afiona.center.stock.domain.aggregate.StoreDistribution>>
     */
    JsonResult<List<StoreDistribution>> listStoreDistribution(List<Long> storeIds);

    /**
     * 查询店铺库存详情
     *
     * @param query
     * @return : com.afiona.common.pojo.JsonResult<com.deepexi.util.pageHelper.PageBean<com.afiona.center.stock.domain.aggregate.WarehouseAggregate>>
     */
    JsonResult<PageBean<WarehouseAggregate>> storeDistributionDetails(StoreDistributionDetailsQuery query);
}
