package com.afiona.center.client.promotion.api;

import com.afiona.center.client.promotion.domain.aggregate.CouponTemplateAggregate;
import com.afiona.center.client.promotion.domain.model.promotionrule.coupon.CouponTemplate;
import com.afiona.center.client.promotion.model.CheckedCouponTemplate;
import com.afiona.center.client.promotion.model.query.CouponTemplateQuery;
import com.afiona.center.client.promotion.model.query.PromotionQuery;
import com.afiona.common.pojo.JsonResult;
import com.deepexi.util.pageHelper.PageBean;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 优惠劵促销活动管理
 *
 * @author dengweiyi
 * @date 2020-02-10
 */
@RequestMapping("/center/v1/coupon/template")
@Api(value = "优惠券模板信息", tags = "优惠券模板信息")
public interface CouponTemplateApi {

    /**
     * 查询
     *
     * @param promotionId 活动主键
     * @return : com.afiona.common.pojo.JsonResult<com.afiona.center.client.promotion.domain.aggregate.CouponTemplateAggregate>
     */
    @GetMapping("/get/promotionId")
    JsonResult<CouponTemplateAggregate> get(@RequestParam("promotionId") long promotionId);

    /**
     * 列表查询
     *
     * @param query 查询条件
     * @return : com.afiona.common.pojo.JsonResult<java.util.List<com.afiona.center.client.promotion.domain.aggregate.CouponTemplateAggregate>>
     */
    @PostMapping("/list/query")
    JsonResult<PageBean<CouponTemplateAggregate>> list(@RequestBody PromotionQuery query);

   /**
     * 全列表查询
     *
     * @param query 查询条件
     * @return 加价购详细信息
     */
    @PostMapping("/listAll/query")
    JsonResult<List<CouponTemplateAggregate>> listAll(@RequestBody PromotionQuery query);

    /**
     * 模板id 查询优惠券模板信息
     *
     * @param templateIds 优惠券模板id 列表
     * @return 优惠券模板信息
     */
    @PostMapping("/list/templateIds")
    JsonResult<List<CouponTemplateAggregate>> list(@RequestBody List<Long> templateIds);

    /**
     * 保存
     *
     * @param couponTemplateAggregate 优惠模板详细信息
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    @PostMapping("/store/couponTemplateAggregate")
    JsonResult store(@RequestBody CouponTemplateAggregate couponTemplateAggregate);

    /**
     * 删除
     *
     * @param id 主键删除
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    @DeleteMapping("/remove/id")
    JsonResult remove(@RequestBody Long id);

    /**
     * 批量删除
     *
     * @param ids 批量主键删除
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    @DeleteMapping("/removeBatch/ids")
    JsonResult removeBatch(@RequestBody List<Long> ids);

    /**
     * 发布类容修改
     *
     * @param couponTemplate 优惠券模板信息
     * @return JsonResult
     */
    @PutMapping("/updateCouponTemplate/couponTemplate")
    JsonResult updateCouponTemplate(@RequestBody CouponTemplate couponTemplate);

   /**
    * 优惠券校验和计算优惠金额
    *
    * @param query 优惠和订单信息
    * @return 优惠券的可用性列表，优惠金额。
    */
    @PostMapping("/checkAndList")
    JsonResult<List<CheckedCouponTemplate>> checkAndList(@RequestBody CouponTemplateQuery query);
}
