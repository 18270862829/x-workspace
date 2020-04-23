package com.afiona.center.stock.api.impl;

import com.afiona.center.stock.api.StockAdminApi;
import com.afiona.center.stock.domain.service.PhysicalStockNumService;
import com.afiona.center.stock.domain.service.StockUpdateService;
import com.afiona.center.stock.model.command.PhysicalStockCommand;
import com.afiona.common.pojo.JsonResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 管理员操作实现
 *
 * @author dengweiyi
 * @date 2020-02-25
 */
@RestController
public class StockAdminApiImpl implements StockAdminApi {
    @Resource
    private PhysicalStockNumService physicalStockNumService;

    @Resource
    private StockUpdateService stockUpdateService;

    @Override
    public JsonResult<Void> in(@RequestBody PhysicalStockCommand command) {
        JsonResult<Void> result = physicalStockNumService.increase(command, true);
        if(!result.success()){
            return result;
        }
        stockUpdateService.update();
        return JsonResult.create();
    }

    @Override
    public JsonResult<Void> out(@RequestBody PhysicalStockCommand command) {
        return physicalStockNumService.reduce(command, false);
    }

    @Override
    public JsonResult<Void> updateStock() {
        stockUpdateService.update();
        return JsonResult.create();
    }
}
