package com.afiona.center.stock.api.impl;

import com.afiona.center.stock.api.WarehouseApi;
import com.afiona.center.stock.domain.aggregate.StoreWarehouses;
import com.afiona.center.stock.domain.aggregate.WarehouseAggregate;
import com.afiona.center.stock.domain.repo.WarehouseRepository;
import com.afiona.center.stock.model.query.WarehouseQuery;
import com.afiona.common.pojo.JsonResult;
import com.deepexi.util.pageHelper.PageBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 仓库管理实现
 *
 * @author dengweiyi
 * @date 2020-02-25
 */
@RestController
public class WarehouseApiImpl implements WarehouseApi {
    @Resource
    private WarehouseRepository warehouseRepository;

    @Override
    public JsonResult<PageBean<WarehouseAggregate>> listPage(@RequestBody WarehouseQuery query) {
        return JsonResult.create(warehouseRepository.listPage(query));
    }

    @Override
    public JsonResult<WarehouseAggregate> getById(@RequestParam(value = "warehouseId", required = false) Long warehouseId) {
        return JsonResult.create(warehouseRepository.getById(warehouseId));
    }

    @Override
    public JsonResult<List<WarehouseAggregate>> listByIds(@RequestBody List<Long> warehouseIds) {
        return JsonResult.create(warehouseRepository.listByIds(warehouseIds));
    }

    @Override
    public JsonResult<Void> store(@RequestBody WarehouseAggregate warehouseAggregate) {
        return warehouseRepository.store(warehouseAggregate);
    }

    @Override
    public JsonResult<Void> switchStatus(long id) {
        return warehouseRepository.switchStatus(id);
    }

    @Override
    public JsonResult<List<StoreWarehouses>> groupByStore(@RequestBody List<Long> storeIds) {
        return JsonResult.create(warehouseRepository.groupByStore(storeIds));
    }
}
