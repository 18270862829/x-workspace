package com.afiona.center.stock.api.impl;

import com.afiona.center.stock.api.StockFlowApi;
import com.afiona.center.stock.domain.aggregate.StockFlowAggregate;
import com.afiona.center.stock.domain.model.StockFlow;
import com.afiona.center.stock.domain.repo.StockFlowRepository;
import com.afiona.center.stock.model.query.StockFlowQuery;
import com.afiona.common.pojo.JsonResult;
import com.deepexi.util.pageHelper.PageBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 库存流水管理实现
 *
 * @author dengweiyi
 * @date 2020-02-27
 */
@RestController
public class StockFlowApiImpl implements StockFlowApi {
    @Resource
    private StockFlowRepository stockFlowRepository;

    @Override
    public JsonResult<PageBean<StockFlowAggregate>> listPage(@RequestBody StockFlowQuery query) {
        return JsonResult.create(stockFlowRepository.listPage(query));
    }
}
