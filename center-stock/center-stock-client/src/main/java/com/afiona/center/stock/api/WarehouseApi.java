package com.afiona.center.stock.api;

import com.afiona.center.stock.domain.aggregate.StoreWarehouses;
import com.afiona.center.stock.domain.aggregate.WarehouseAggregate;
import com.afiona.center.stock.model.query.WarehouseQuery;
import com.afiona.common.pojo.JsonResult;
import com.deepexi.util.pageHelper.PageBean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 仓库管理
 *
 * @author dengweiyi
 * @date 2020-02-25
 */
@RequestMapping("/warehouse")
public interface WarehouseApi {
    /**
     * 分页查询
     *
     * @param query
     * @return : java.util.List<com.afiona.center.stock.domain.aggregate.WarehouseAggregate>
     */
    @PostMapping("/listPage")
    JsonResult<PageBean<WarehouseAggregate>> listPage(@RequestBody WarehouseQuery query);

    /**
     * 查询
     *
     * @param warehouseId
     * @return : com.afiona.common.pojo.JsonResult<com.afiona.center.stock.domain.aggregate.WarehouseAggregate>
     */
    @PostMapping("/getById")
    JsonResult<WarehouseAggregate> getById(@RequestParam(value = "warehouseId", required = false) Long warehouseId);

    /**
     * 批量查询
     *
     * @param warehouseIds
     * @return : com.afiona.common.pojo.JsonResult<java.util.List<com.afiona.center.stock.domain.aggregate.WarehouseAggregate>>
     */
    @PostMapping("/listByIds")
    JsonResult<List<WarehouseAggregate>> listByIds(List<Long> warehouseIds);

    /**
     * 保存
     *
     * @param warehouseAggregate
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    @PostMapping("/store")
    JsonResult<Void> store(@RequestBody WarehouseAggregate warehouseAggregate);

    /**
     * 切换状态
     *
     * @param id
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    @PostMapping("/switchStatus")
    JsonResult<Void> switchStatus(@RequestParam("id") long id);

    /**
     * 查询仓库列表（根据店铺分组）
     *
     * @param storeIds
     * @return : com.afiona.common.pojo.JsonResult<java.util.List<com.afiona.center.stock.domain.aggregate.StoreWarehouses>>
     */
    @PostMapping("/groupByStore")
    JsonResult<List<StoreWarehouses>> groupByStore(@RequestBody List<Long> storeIds);
}
