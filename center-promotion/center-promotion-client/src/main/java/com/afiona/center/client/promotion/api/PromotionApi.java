package com.afiona.center.client.promotion.api;


import com.afiona.center.client.promotion.domain.aggregate.PromotionAggregate;
import com.afiona.center.client.promotion.domain.model.Promotion;
import com.afiona.center.client.promotion.model.*;
import com.afiona.center.client.promotion.model.annotation.PromotionPoint;
import com.afiona.center.client.promotion.model.put.PromotionEnabledSettingPut;
import com.afiona.center.client.promotion.model.query.*;
import com.afiona.common.pojo.JsonResult;
import com.deepexi.util.pageHelper.PageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 促销活动管理（接口调用者不关心促销活动为具体的什么活动类型）
 *
 * @author dengweiyi
 * @date 2020-02-10
 */
@RequestMapping("/center/v1/promotion/promotion")
@Api(value = "促销活动", tags = "促销活动")
public interface PromotionApi {

    /**
     * 促销活动查询
     *
     * @param promotionId 活动id
     * @return : 活动基础信息
     */
    @GetMapping("/get/promotionId")
    @ApiOperation("促销活动查询")
    JsonResult<Promotion> get(Long promotionId);

    /**
     * 商品促销活动查询（可获取已启用、进行中的促销活动且在活动时间内的促销活动）
     *
     * @param skuIds 商品skuId列表
     * @return : com.afiona.common.pojo.JsonResult<com.afiona.center.client.promotion.model.PromotionGroup>
     */
    @PostMapping("/listGroupBySkuIds/skuIds")
    @ApiOperation("商品促销活动查询（可获取已启用且进行中且在活动时间内的促销活动）")
    JsonResult<List<PromotionGroup>> listGroupBySkuIds(@RequestBody List<Long> skuIds);

    /**
     * 促销活动查询（只会获取单品促销活动）
     *
     * @param skuIds 商品skuId列表
     * @return skuId和活动的映射
     */
    @PostMapping("/listSinglePromotionBySkuIds/skuIds")
    @ApiOperation("促销活动查询（只会已启用、进行中且在活动时间内的单品促销活动）")
    JsonResult<Map<Long,List<Promotion>>> listSinglePromotionBySkuIds(@RequestBody List<Long> skuIds);

    /**
     * 活动详情
     *
     * @param promotionId 活动id
     * @return : com.afiona.common.pojo.JsonResult<com.afiona.center.client.promotion.domain.aggregate.PromotionAggregate>
     */
    @GetMapping("/details/promotionId")
    @ApiOperation("活动详情")
    JsonResult<PromotionAggregate> details(@RequestParam("promotionId") long promotionId);

    /**
     * 促销基础信息列表
     *
     * @param query 查询条件
     * @return 活动基础信息
     */
    @PostMapping("/list/query")
    @ApiOperation("促销基础信息列表")
    JsonResult<PageBean<Promotion>> list(@RequestBody PromotionQuery query);

    /**
     * 全列表查询
     *
     * @param query 查询条件
     * @return 加价购详细信息
     */
    @PostMapping("/listAll/query")
    @ApiOperation("全列表查询")
    JsonResult<List<Promotion>> listAll(@RequestBody PromotionQuery query);

    /**
     * 单品促销福利计算
     *
     * @param query 商品 信息
     * @return 商品 对应 福利
     */
    @PostMapping("/goodsBenefit/query")
    @ApiOperation("单品促销福利计算")
    JsonResult<List<SingleCommoditiesBenefit>> goodsBenefit(@RequestBody GoodsBenefitCalcQuery query);

    /**
     * 支付有礼福利计算
     *
     * @param query 支付信息
     * @return 福利信息
     */
    @PostMapping("/payGiftBenefit/")
    @ApiOperation("支付有礼福利计算")
    JsonResult<List<PayGiftBenefit>> payGiftBenefit(@RequestBody PayGiftBenefitCalcQuery query);

    /**
     * 促销活动启用或停用
     *
     * @param put 启用或停用条件参数
     * @return JsonResult
     */
    @PostMapping("/enabledSetting/promotionId/enabled")
    @ApiOperation("促销活动启用或停用")
    JsonResult enabledSetting(@RequestBody PromotionEnabledSettingPut put);

    /**
     * 最优整体促销活动查询(只会获取整体促销、启用促销、状态是进行中的促销和在活动时间内的促销活动)
     *
     * @param skuId 商品skuId
     * @return 最优促销基础活动信息
     */
    @GetMapping("/optimalPromotion/skuId")
    @ApiOperation("最优整体促销活动查询(只会获取整体促销、启用促销、状态是进行中的促销且在活动时间内的促销活动)")
    JsonResult<Promotion> optimalPromotion(@RequestParam("skuId") Long skuId);

    /**
     * 最优整体促销活动查询
     *
     * @param skuIds 商品skuIds列表
     * @return 最优促销基础活动信息列表
     */
    @PostMapping("/optimalPromotion/skuIds")
    @ApiOperation("最优整体促销活动查询")
    JsonResult<Map<Long,Promotion>> optimalPromotion(@RequestBody List<Long> skuIds);

    /**
     * skuId可参与的促销列表
     *
     * @param skuId 商品 skuId
     * @return 可参与的活动列表
     */
    @PostMapping("/promotionListBySkuId/skuId")
    @ApiOperation("skuId可参与的促销列表")
    JsonResult<List<Promotion>> promotionListBySkuId(@RequestParam("skuId") Long skuId);

    /**
     * skuId可参与的促销列表
     *
     * @param skuIds 商品skuId列表
     * @return 可参与的活动列表
     */
    @PostMapping("/promotionListBySkuIds/skuIds")
    @ApiOperation("skuId可参与的促销列表")
    JsonResult<Map<Long,List<Promotion>>> promotionListBySkuIds(@RequestBody List<Long> skuIds);

     /**
     * 福利计算
     *
     * @param query 福利计算条件
     * @return 福利信息
     */
    @PostMapping("/preferentialCalculation/query")
    @ApiOperation("福利计算")
    JsonResult<MaterialBenefits> preferentialCalculation(@RequestBody BenefitCalcQuery query);

    /**
     * 整体促销适用商品查询
     *
     * @param query 促销适用商品查询条件
     * @return 促销适用商品
     */
    @GetMapping("/getPromotionSuitGoods/query")
    @ApiOperation("整体促销适用商品查询")
    JsonResult<PromotionSuitGoods> getPromotionSuitGoods(PromotionSuitGoodsQuery query);

}
