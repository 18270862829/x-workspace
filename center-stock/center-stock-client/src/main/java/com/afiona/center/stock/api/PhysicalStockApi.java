package com.afiona.center.stock.api;

import com.afiona.center.stock.domain.aggregate.StockDistribution;
import com.afiona.center.stock.domain.aggregate.PhysicalStockAggregate;
import com.afiona.center.stock.domain.aggregate.StoreDistribution;
import com.afiona.center.stock.domain.aggregate.WarehouseAggregate;
import com.afiona.center.stock.model.command.PhysicalStockCommand;
import com.afiona.center.stock.model.query.PhysicalStockQuery;
import com.afiona.center.stock.model.query.StoreDistributionDetailsQuery;
import com.afiona.common.pojo.JsonResult;
import com.deepexi.util.pageHelper.PageBean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 实物库存管理
 *
 * @author dengweiyi
 * @date 2020-02-25
 */
@RequestMapping("/physicalStock")
public interface PhysicalStockApi {
    /**
     * 分页查询（按SKU ID分组）
     *
     * @param query
     * @return : java.util.List<com.afiona.center.stock.domain.aggregate.StockDistribution>
     */
    @PostMapping("/listPage")
    JsonResult<PageBean<StockDistribution>> listPage(@RequestBody PhysicalStockQuery query);

    /**
     * 查询库存分布列表（根据店铺ID）
     *
     * @param storeId
     * @return : com.afiona.common.pojo.JsonResult<java.util.List<com.afiona.center.stock.domain.aggregate.PhysicalStockAggregate>>
     */
    @PostMapping("/listByChannelId")
    JsonResult<List<PhysicalStockAggregate>> listByStoreId(@RequestParam("storeId") long storeId);

    /**
     * 列表查询店铺库存分布
     *
     * @param storeIds
     * @return : com.afiona.common.pojo.JsonResult<java.util.List<com.afiona.center.stock.domain.aggregate.StoreDistribution>>
     */
    @PostMapping("/listStoreDistribution")
    JsonResult<List<StoreDistribution>> listStoreDistribution(@RequestBody List<Long> storeIds);

    /**
     * 查询店铺库存详情
     *
     * @param query
     * @return : com.afiona.common.pojo.JsonResult<com.deepexi.util.pageHelper.PageBean<com.afiona.center.stock.domain.aggregate.WarehouseAggregate>>
     */
    @PostMapping("/storeDistributionDetails")
    JsonResult<PageBean<WarehouseAggregate>> storeDistributionDetails(@RequestBody StoreDistributionDetailsQuery query);

    /**
     * 冻结库存
     *
     * @param command
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    @PostMapping("/freeze")
    JsonResult<Void> freeze(@RequestBody PhysicalStockCommand command);

    /**
     * 解冻库存
     *
     * @param command
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Long>
     */
    @PostMapping("/unfreeze")
    JsonResult<Void> unfreeze(@RequestBody PhysicalStockCommand command);

    /**
     * 扣减库存
     *
     * @param command
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    @PostMapping("/reduce")
    JsonResult<Void> reduce(@RequestBody PhysicalStockCommand command);

    /**
     * 增加库存
     *
     * @param command
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    @PostMapping("/increase")
    JsonResult<Void> increase(@RequestBody PhysicalStockCommand command);
}
