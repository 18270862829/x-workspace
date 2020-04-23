package com.afiona.center.client.promotion.api;

import com.afiona.center.client.promotion.domain.aggregate.SuitGroupAggregate;
import com.afiona.center.client.promotion.model.query.PromotionQuery;
import com.afiona.common.pojo.JsonResult;
import com.deepexi.util.pageHelper.PageBean;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 组合套装促销活动管理
 *
 * @author LiJinXing
 * @date 2020/2/23
 */
@RequestMapping("/center/v1/suit/group")
@Api(value ="组合套装促销活动管理",tags = "组合套装促销活动管理")
public interface SuitGroupApi {

    /**
     * 查询
     *
     * @param promotionId 活动id
     * @return 组合套装详细信息
     */
    @GetMapping("/get/promotionId")
    JsonResult<SuitGroupAggregate> get(@RequestParam("promotionId") long promotionId);

    /**
     * 列表查询
     *
     * @param query 查询条件
     * @return 组合套装详细信息
     */
    @PostMapping("/list/query")
    JsonResult<PageBean<SuitGroupAggregate>> list(@RequestBody PromotionQuery query);

    /**
     * 全列表查询
     *
     * @param query 查询条件
     * @return 加价购详细信息
     */
    @PostMapping("/listAll/query")
    JsonResult<List<SuitGroupAggregate>> listAll(@RequestBody PromotionQuery query);

    /**
     * 保存
     *
     * @param suitGroupAggregate 组合套装聚合根
     * @return com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    @PostMapping("/store/suitGroupAggregate")
    JsonResult store(@RequestBody SuitGroupAggregate suitGroupAggregate);

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
    @DeleteMapping("/removeBatch/list")
    JsonResult removeBatch(@RequestBody List<Long> ids);

    /**
     * 套装删除
     *
     * @param suitFixedSuitRuleId 套装id
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    @DeleteMapping("/removeSuitFixedSuitRule/suitFixedSuitRuleId")
    JsonResult removeSuitFixedSuitRule(@RequestBody Long suitFixedSuitRuleId);

    /**
     * 关联商品删除
     *
     * @param suitFixedSuitGoodsId 套装关联商品id
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    @DeleteMapping("/removeSuitFixedSuitGoods/suitFixedSuitGoodsId")
    JsonResult removeSuitFixedSuitGoods(@RequestBody Long suitFixedSuitGoodsId);

}
