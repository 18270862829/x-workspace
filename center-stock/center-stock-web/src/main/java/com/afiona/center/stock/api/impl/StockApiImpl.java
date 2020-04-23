package com.afiona.center.stock.api.impl;

import com.afiona.center.stock.api.StockApi;
import com.afiona.center.stock.domain.model.Stock;
import com.afiona.center.stock.model.command.StockCommand;
import com.afiona.center.stock.model.query.StockPageQuery;
import com.afiona.center.stock.model.query.StockQuery;
import com.afiona.center.stock.domain.repo.StockRepository;
import com.afiona.center.stock.domain.service.StockNumService;
import com.afiona.common.pojo.JsonResult;
import com.deepexi.util.pageHelper.PageBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 销售库存管理实现
 *
 * @author dengweiyi
 * @date 2020-02-25
 */
@RestController
public class StockApiImpl implements StockApi {
    @Resource
    private StockRepository stockRepository;

    @Resource
    private StockNumService stockNumService;

    @Override
    public JsonResult<PageBean<Stock>> listPage(@RequestBody StockPageQuery query) {
        return JsonResult.create(stockRepository.listPage(query));
    }

    @Override
    public JsonResult<List<Stock>> listStock(@RequestBody StockQuery query) {
        return JsonResult.create(stockRepository.listStock(query));
    }

    @Override
    public JsonResult<Void> freeze(@RequestBody StockCommand command) {
        return stockNumService.freeze(command);
    }

    @Override
    public JsonResult<Void> unfreeze(@RequestBody StockCommand command) {
        return stockNumService.unfreeze(command);
    }

    @Override
    public JsonResult<Void> reduce(@RequestBody StockCommand command) {
        return stockNumService.reduce(command, false);
    }

    @Override
    public JsonResult<Void> increase(@RequestBody StockCommand command) {
        return stockNumService.increase(command, false);
    }
}
