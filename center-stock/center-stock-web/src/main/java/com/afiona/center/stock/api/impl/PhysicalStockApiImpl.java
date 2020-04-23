package com.afiona.center.stock.api.impl;

import com.afiona.center.stock.api.PhysicalStockApi;
import com.afiona.center.stock.domain.aggregate.StockDistribution;
import com.afiona.center.stock.domain.aggregate.PhysicalStockAggregate;
import com.afiona.center.stock.domain.aggregate.StoreDistribution;
import com.afiona.center.stock.domain.aggregate.WarehouseAggregate;
import com.afiona.center.stock.domain.repo.PhysicalStockRepository;
import com.afiona.center.stock.domain.service.PhysicalStockNumService;
import com.afiona.center.stock.model.command.PhysicalStockCommand;
import com.afiona.center.stock.model.query.PhysicalStockQuery;
import com.afiona.center.stock.model.query.StoreDistributionDetailsQuery;
import com.afiona.common.pojo.JsonResult;
import com.deepexi.util.pageHelper.PageBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 实物库存管理实现
 *
 * @author dengweiyi
 * @date 2020-02-25
 */
@RestController
public class PhysicalStockApiImpl implements PhysicalStockApi {
    @Resource
    private PhysicalStockRepository physicalStockRepository;

    @Resource
    private PhysicalStockNumService physicalStockNumService;

    @Override
    public JsonResult<PageBean<StockDistribution>> listPage(@RequestBody PhysicalStockQuery query) {
        return JsonResult.create(physicalStockRepository.listPage(query));
    }

    @Override
    public JsonResult<List<PhysicalStockAggregate>> listByStoreId(long storeId) {
        return JsonResult.create(physicalStockRepository.listByStoreId(storeId));
    }

    @Override
    public JsonResult<List<StoreDistribution>> listStoreDistribution(@RequestBody List<Long> storeIds) {
        return physicalStockRepository.listStoreDistribution(storeIds);
    }

    @Override
    public JsonResult<PageBean<WarehouseAggregate>> storeDistributionDetails(@RequestBody StoreDistributionDetailsQuery query) {
        return physicalStockRepository.storeDistributionDetails(query);
    }

    @Override
    public JsonResult<Void> freeze(@RequestBody PhysicalStockCommand command) {
        return physicalStockNumService.freeze(command);
    }

    @Override
    public JsonResult<Void> unfreeze(@RequestBody PhysicalStockCommand command) {
        return physicalStockNumService.unfreeze(command);
    }

    @Override
    public JsonResult<Void> reduce(@RequestBody PhysicalStockCommand command) {
        return physicalStockNumService.reduce(command, true);
    }

    @Override
    public JsonResult<Void> increase(@RequestBody PhysicalStockCommand command) {
        return physicalStockNumService.increase(command, false);
    }
}
