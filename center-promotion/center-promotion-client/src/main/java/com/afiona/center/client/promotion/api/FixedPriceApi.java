package com.afiona.center.client.promotion.api;

import com.afiona.center.client.promotion.domain.aggregate.FixedPriceAggregate;
import com.afiona.center.client.promotion.model.query.PromotionQuery;
import com.afiona.common.pojo.JsonResult;
import com.deepexi.util.pageHelper.PageBean;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 一口价促销活动管理
 *
 * @author dengweiyi
 * @date 2020-02-10
 */
@RequestMapping("/center/v1/fixed/price")
@Api(value = "一口价促销活动管理", tags = "一口价促销活动管理")
public interface FixedPriceApi {

    /**
     * 查询
     *
     * @param promotionId 活动id
     * @return 一口价详细信息
     */
    @GetMapping("/get/promotionId")
    JsonResult<FixedPriceAggregate> get(@RequestParam("promotionId") long promotionId);

    /**
     * 列表查询
     *
     * @param query 查询条件
     * @return 一口价详细信息
     */
    @PostMapping("/list/query")
    JsonResult<PageBean<FixedPriceAggregate>> list(@RequestBody PromotionQuery query);

    /**
     * 全列表查询
     *
     * @param query 查询条件
     * @return 加价购详细信息
     */
    @PostMapping("/listAll/query")
    JsonResult<List<FixedPriceAggregate>> listAll(@RequestBody PromotionQuery query);

    /**
     * 保存
     *
     * @param fixedPriceAggregate 一口价聚合根
     * @return com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    @PostMapping("/store/fixedPriceAggregate")
    JsonResult store(@RequestBody FixedPriceAggregate fixedPriceAggregate);

    /**
     * 删除
     *
     * @param  id 主键
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    @DeleteMapping("/remove/fixedPriceAggregate")
    JsonResult remove(@RequestBody Long id);

    /**
     * 批量删除
     *
     * @param ids 主键；列表
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    @DeleteMapping("/removeBatch/ids")
    JsonResult removeBatch(@RequestBody List<Long> ids);

    /**
     * 折扣商品删除
     *
     * @param fixedPriceGoodsIds 折后商品列表主键
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    @DeleteMapping("/removeFixedPriceGoodsIds/fixedPriceGoodsIds")
    JsonResult removeFixedPriceGoodsIds(@RequestBody List<Long> fixedPriceGoodsIds);
}
