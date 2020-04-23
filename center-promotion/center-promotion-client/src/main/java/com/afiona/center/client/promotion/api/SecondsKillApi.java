package com.afiona.center.client.promotion.api;

import com.afiona.center.client.promotion.domain.aggregate.SecondsKillAggregate;
import com.afiona.center.client.promotion.model.query.PromotionQuery;
import com.afiona.common.pojo.JsonResult;
import com.deepexi.util.pageHelper.PageBean;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 秒杀管理
 *
 * @author LiJinXing
 * @date 2020/4/7
 */
@RequestMapping("/center/v1/seconds/kill")
@Api(value ="秒杀管理",tags = "秒杀管理")
public interface SecondsKillApi {

    /**
     * 查询
     *
     * @param promotionId 活动id
     * @return 限时折扣详细信息
     */
    @GetMapping("/get/promotionId")
    JsonResult<SecondsKillAggregate> get(@RequestParam("promotionId") long promotionId);

    /**
     * 列表查询
     *
     * @param query 查询条件
     * @return 限时折扣详细信息
     */
    @PostMapping("/list/query")
    JsonResult<PageBean<SecondsKillAggregate>> list(@RequestBody PromotionQuery query);

    /**
     * 全列表查询
     *
     * @param query 查询条件
     * @return 加价购详细信息
     */
    @PostMapping("/listAll/query")
    JsonResult<List<SecondsKillAggregate>> listAll(@RequestBody PromotionQuery query);

    /**
     * 保存
     *
     * @param secondsKillAggregate 限时折扣聚合根
     * @return com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    @PostMapping("/store/specialDiscountAggregate")
    JsonResult store(@RequestBody SecondsKillAggregate secondsKillAggregate);

    /**
     * 删除
     *
     * @param  id 主键
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
}
