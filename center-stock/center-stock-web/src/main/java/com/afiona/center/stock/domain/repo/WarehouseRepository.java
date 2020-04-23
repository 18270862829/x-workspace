package com.afiona.center.stock.domain.repo;

import com.afiona.center.stock.domain.aggregate.StoreWarehouses;
import com.afiona.center.stock.domain.aggregate.WarehouseAggregate;
import com.afiona.center.stock.model.query.WarehouseQuery;
import com.afiona.common.pojo.JsonResult;
import com.deepexi.util.pageHelper.PageBean;

import java.util.List;

/**
 * 仓库repo
 *
 * @author dengweiyi
 * @date 2020-02-25
 */
public interface WarehouseRepository {
    /**
     * 分页查询
     *
     * @param query
     * @return : java.util.List<com.afiona.center.stock.domain.aggregate.WarehouseAggregate>
     */
    PageBean<WarehouseAggregate> listPage(WarehouseQuery query);

    /**
     * 查询
     *
     * @param warehouseId
     * @return : com.afiona.center.stock.domain.aggregate.WarehouseAggregate
     */
    WarehouseAggregate getById(long warehouseId);

    /**
     * 列表查询
     *
     * @param warehouseIds
     * @return : java.util.List<com.afiona.center.stock.domain.aggregate.WarehouseAggregate>
     */
    List<WarehouseAggregate> listByIds(List<Long> warehouseIds);

    /**
     * 保存
     *
     * @param warehouseAggregate
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    JsonResult<Void> store(WarehouseAggregate warehouseAggregate);

    /**
     * 切换状态
     *
     * @param id
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    JsonResult<Void> switchStatus(long id);

    /**
     * 查询仓库列表（根据店铺分组）
     *
     * @param storeIds
     * @return : java.util.List<com.afiona.center.stock.domain.aggregate.StoreWarehouses>
     */
    List<StoreWarehouses> groupByStore(List<Long> storeIds);
}
