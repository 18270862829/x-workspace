package com.afiona.center.client.promotion.api;

import com.afiona.center.client.promotion.domain.aggregate.FullReductionAggregate;
import com.afiona.center.client.promotion.domain.aggregate.PayGiftAggregate;
import com.afiona.center.client.promotion.model.query.PromotionQuery;
import com.afiona.common.pojo.JsonResult;
import com.deepexi.util.pageHelper.PageBean;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 支付有礼促销活动管理
 *
 * @author LiJinXing
 * @date 2020/2/25
 */
@RequestMapping("/center/v1/pay/gift")
@Api(value = "支付有礼促销活动管理", tags = "支付有礼促销活动管理")
public interface PayGiftApi {

    /**
     * 查询
     *
     * @param promotionId 活动id
     * @return 加价购详细信息
     */
    @GetMapping("/get/promotionId")
    JsonResult<PayGiftAggregate> get(@RequestParam("promotionId") long promotionId);

    /**
     * 列表查询
     *
     * @param query 查询条件
     * @return 加价购详细信息
     */
    @PostMapping("/list/query")
    JsonResult<PageBean<PayGiftAggregate>> list(@RequestBody PromotionQuery query);

    /**
     * 全列表查询
     *
     * @param query 查询条件
     * @return 加价购详细信息
     */
    @PostMapping("/listAll/query")
    JsonResult<List<PayGiftAggregate>> listAll(@RequestBody PromotionQuery query);

    /**
     * 保存
     *
     * @param payGiftAggregate 加价购聚合根
     * @return com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    @PostMapping("/store/payGiftAggregate")
    JsonResult store(@RequestBody PayGiftAggregate payGiftAggregate);

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
     * 阶梯规则删除
     *
     * @param payGiftStairRuleIds 阶梯规则列表主键
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    @DeleteMapping("/removePayGiftStairRuleIds/payGiftStairRuleIds")
    JsonResult removePayGiftStairRuleIds(@RequestBody List<Long> payGiftStairRuleIds);
}
