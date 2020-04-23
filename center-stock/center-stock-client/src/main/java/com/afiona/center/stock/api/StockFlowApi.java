package com.afiona.center.stock.api;

import com.afiona.center.stock.domain.aggregate.StockFlowAggregate;
import com.afiona.center.stock.model.query.StockFlowQuery;
import com.afiona.common.pojo.JsonResult;
import com.deepexi.util.pageHelper.PageBean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 库存流水管理
 *
 * @author dengweiyi
 * @date 2020-02-27
 */
@RequestMapping("/stock/flow")
public interface StockFlowApi {
    /**
     * 分页查询
     *
     * @param query
     * @return : com.afiona.common.pojo.JsonResult<java.util.List<com.afiona.center.stock.domain.aggregate.StockFlowAggregate>>
     */
    @PostMapping("/listPage")
    JsonResult<PageBean<StockFlowAggregate>> listPage(@RequestBody StockFlowQuery query);
}
