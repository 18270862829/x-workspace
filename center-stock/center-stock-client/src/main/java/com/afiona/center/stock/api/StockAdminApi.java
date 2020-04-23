package com.afiona.center.stock.api;

import com.afiona.center.stock.model.command.PhysicalStockCommand;
import com.afiona.common.pojo.JsonResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 管理员操作
 *
 * @author dengweiyi
 * @date 2020-02-25
 */
@RequestMapping("/stock/admin")
public interface StockAdminApi {
    /**
     * 入库
     *
     * @param command
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    @PostMapping("/in")
    JsonResult<Void> in(@RequestBody PhysicalStockCommand command);

    /**
     * 出库
     *
     * @param command
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    @PostMapping("/out")
    JsonResult<Void> out(@RequestBody PhysicalStockCommand command);

    /**
     * 更新销售库存（本身应该是定时任务，暂开放接口支持测试）
     *
     * @param
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    @PostMapping("/updateStock")
    JsonResult<Void> updateStock();
}
