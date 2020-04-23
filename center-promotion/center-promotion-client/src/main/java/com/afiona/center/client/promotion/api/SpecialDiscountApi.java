package com.afiona.center.client.promotion.api;

import com.afiona.center.client.promotion.domain.aggregate.SpecialDiscountAggregate;
import com.afiona.center.client.promotion.model.query.PromotionQuery;
import com.afiona.common.pojo.JsonResult;
import com.deepexi.util.pageHelper.PageBean;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 限时折扣促销活动管理
 *
 * @author dengweiyi
 * @date 2020-02-10
 */
@RequestMapping("/center/v1/special/discount")
@Api(value ="限时折扣促销活动管理",tags = "限时折扣促销活动管理")
public interface SpecialDiscountApi {

    /**
     * 查询
     *
     * @param promotionId 活动id
     * @return 限时折扣详细信息
     */
    @GetMapping("/get/promotionId")
    JsonResult<SpecialDiscountAggregate> get(@RequestParam("promotionId") long promotionId);

    /**
     * 列表查询
     *
     * @param query 查询条件
     * @return 限时折扣详细信息
     */
    @PostMapping("/list/query")
    JsonResult<PageBean<SpecialDiscountAggregate>> list(@RequestBody PromotionQuery query);

    /**
     * 全列表查询
     *
     * @param query 查询条件
     * @return 加价购详细信息
     */
    @PostMapping("/listAll/query")
    JsonResult<List<SpecialDiscountAggregate>> listAll(@RequestBody PromotionQuery query);

    /**
     * 保存
     *
     * @param specialDiscountAggregate 限时折扣聚合根
     * @return com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    @PostMapping("/store/specialDiscountAggregate")
    JsonResult store(@RequestBody SpecialDiscountAggregate specialDiscountAggregate);

    /**
     * 删除
     *
     * @param id  主键
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
     * 关联商品删除
     *
     * @param specialDiscountGoodsIds 活动商品主键列表
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    @DeleteMapping("/removeSpecialDiscountGoodsIds/specialDiscountGoodsIds")
    JsonResult removeSpecialDiscountGoodsIds(@RequestBody List<Long> specialDiscountGoodsIds);
}
