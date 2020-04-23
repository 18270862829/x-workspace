package com.afiona.center.client.promotion.api;

import com.afiona.center.client.promotion.domain.aggregate.FullReductionAggregate;
import com.afiona.center.client.promotion.model.query.PromotionQuery;
import com.afiona.common.pojo.JsonResult;
import com.deepexi.util.pageHelper.PageBean;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 满减促销活动管理
 *
 * @author dengweiyi
 * @date 2020-02-10
 */
@RequestMapping("/center/v1/full/reduction")
@Api(value ="满减促销活动管理",tags = "满减促销活动管理")
public interface FullReductionApi {
    /**
     * 查询
     *
     * @param promotionId
     * @return : com.afiona.common.pojo.JsonResult<com.afiona.center.client.promotion.domain.aggregate.FullReductionAggregate>
     */
    @GetMapping("/get/promotionId")
    JsonResult<FullReductionAggregate> get(@RequestParam("promotionId") long promotionId);

    /**
     * 列表查询
     *
     * @param query
     * @return : com.afiona.common.pojo.JsonResult<java.util.List<com.afiona.center.client.promotion.domain.aggregate.FullReductionAggregate>>
     */
    @PostMapping("/list/query")
    JsonResult<PageBean<FullReductionAggregate>> list(@RequestBody PromotionQuery query);

    /**
     * 全列表查询
     *
     * @param query 查询条件
     * @return 加价购详细信息
     */
    @PostMapping("/listAll/query")
    JsonResult<List<FullReductionAggregate>> listAll(@RequestBody PromotionQuery query);

    /**
     * 保存
     *
     * @param fullReductionAggregate
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    @PostMapping("/store/fullReductionAggregate")
    JsonResult store(@RequestBody FullReductionAggregate fullReductionAggregate);

    /**
     * 删除
     *
     * @param id 主键
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    @DeleteMapping("/remove/id")
    JsonResult remove(@RequestBody Long id);

    /**
     * 批量删除
     *
     * @param ids 主键列表
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    @DeleteMapping("/removeBatch/ids")
    JsonResult removeBatch(@RequestBody List<Long> ids);

    /**
     * 阶梯或循环规则删除
     *
     * @param id 阶梯或循环规则id
     * @return JsonResult
     */
    @DeleteMapping("removeFullReductionStairRule/id")
    JsonResult removeFullReductionStairRule(@RequestBody Long id);
}
