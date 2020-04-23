package com.afiona.center.stock.api;

import com.afiona.center.stock.domain.model.Stock;
import com.afiona.center.stock.model.command.StockCommand;
import com.afiona.center.stock.model.query.StockPageQuery;
import com.afiona.center.stock.model.query.StockQuery;
import com.afiona.common.pojo.JsonResult;
import com.deepexi.util.pageHelper.PageBean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 销售库存管理
 *
 * @author dengweiyi
 * @date 2020-01-06
 */
@RequestMapping("/stock")
public interface StockApi {
    /**
     * 分页查询（按店铺分组）
     *
     * @param query
     * @return : java.util.List<com.afiona.center.stock.domain.model.Stock>
     */
    @PostMapping("/listPage")
    JsonResult<PageBean<Stock>> listPage(@RequestBody StockPageQuery query);

    /**
     * 查询库存列表
     *
     * @param query
     * @return : com.afiona.common.pojo.JsonResult<java.util.List<Stock>>
     */
    @PostMapping("/list")
    JsonResult<List<Stock>> listStock(@RequestBody StockQuery query);

    /**
     * 冻结库存
     *
     * @param command
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    @PostMapping("/freeze")
    JsonResult<Void> freeze(@RequestBody StockCommand command);

    /**
     * 解冻库存
     *
     * @param command
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Long>
     */
    @PostMapping("/unfreeze")
    JsonResult<Void> unfreeze(@RequestBody StockCommand command);

    /**
     * 扣减库存
     *
     * @param command
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    @PostMapping("/reduce")
    JsonResult<Void> reduce(@RequestBody StockCommand command);

    /**
     * 增加库存
     *
     * @param command
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    @PostMapping("/increase")
    JsonResult<Void> increase(@RequestBody StockCommand command);
}
