package com.afiona.center.client.promotion.api;

import com.afiona.center.client.promotion.domain.aggregate.PresellAggregate;
import com.afiona.center.client.promotion.domain.model.promotionrule.presell.PresellGoods;
import com.afiona.center.client.promotion.model.query.PromotionQuery;
import com.afiona.common.pojo.JsonResult;
import com.deepexi.util.pageHelper.PageBean;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 预售促销管理
 *
 * @author LiJinXing
 * @date 2020/4/22
 */
@RequestMapping("/center/v1/presell")
@Api(value = "预售促销管理", tags = "预售促销管理")
public interface PresellApi {

    /**
     * 查询
     *
     * @param promotionId 活动id
     * @return 加价购详细信息
     */
    @GetMapping("/get/promotionId")
    JsonResult<PresellAggregate> get(@RequestParam("promotionId") long promotionId);

    /**
     * 列表查询
     *
     * @param query 查询条件
     * @return 加价购详细信息
     */
    @PostMapping("/list/query")
    JsonResult<PageBean<PresellAggregate>> list(@RequestBody PromotionQuery query);

    /**
     * 全列表查询
     *
     * @param query 查询条件
     * @return 加价购详细信息
     */
    @PostMapping("/listAll/query")
    JsonResult<List<PresellAggregate>> listAll(@RequestBody PromotionQuery query);

    /**
     * 保存
     *
     * @param presellAggregate 预售聚合根
     * @return com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    @PostMapping("/store/payGiftAggregate")
    JsonResult store(@RequestBody PresellAggregate presellAggregate);

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
     * 预售商品删除
     *
     * @param presellGoodsId 预售商品id
     * @return JsonResult
     */
    @DeleteMapping("/removePresellGoods/presellGoodsId")
    JsonResult removePresellGoods(@RequestBody Long presellGoodsId);

    /**
     * 预售商品编辑
     *
     * @param presellGoods 预售商品
     * @return JsonResult
     */
    @PostMapping("/updatePresellGoods/presellGoods")
    JsonResult updatePresellGoods(@RequestBody PresellGoods presellGoods);
}
