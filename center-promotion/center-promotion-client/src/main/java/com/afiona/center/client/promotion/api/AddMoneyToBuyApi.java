package com.afiona.center.client.promotion.api;

import com.afiona.center.client.promotion.domain.aggregate.AddMoneyToBuyAggregate;
import com.afiona.center.client.promotion.domain.aggregate.SuitGroupAggregate;
import com.afiona.center.client.promotion.model.query.PromotionQuery;
import com.afiona.common.pojo.JsonResult;
import com.deepexi.util.pageHelper.PageBean;
import io.swagger.annotations.Api;
import lombok.Data;
import org.codehaus.groovy.syntax.CSTNode;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 加价购促销活动管理
 *
 * @author LiJinXing
 * @date 2020/2/23
 */
@RequestMapping("/center/v1/add/money/to/buy")
@Api(value = "加价购促销活动", tags = "加价购促销活动")
public interface AddMoneyToBuyApi {

    /**
     * 查询
     *
     * @param promotionId 活动id
     * @return 加价购详细信息
     */
    @GetMapping("/get/promotionId")
    JsonResult<AddMoneyToBuyAggregate> get(@RequestParam("promotionId") long promotionId);

    /**
     * 列表查询
     *
     * @param query 查询条件
     * @return 加价购详细信息
     */
    @PostMapping("/list/query")
    JsonResult<PageBean<AddMoneyToBuyAggregate>> list(@RequestBody PromotionQuery query);

    /**
     * 全列表查询
     *
     * @param query 查询条件
     * @return 加价购详细信息
     */
    @PostMapping("/listAll/query")
    JsonResult<List<AddMoneyToBuyAggregate>> listAll(@RequestBody PromotionQuery query);

    /**
     * 保存
     *
     * @param addMoneyToBuyAggregate 加价购聚合根
     * @return com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    @PostMapping("/store/addMoneyToBuyAggregate")
    JsonResult store(@RequestBody AddMoneyToBuyAggregate addMoneyToBuyAggregate);

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
     * 换购商品删除
     *
     * @param addMoneyToBuyGoodsIds 换购商品对象id
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    @DeleteMapping("/removeAddMoneyToBuyGoodsIds/addMoneyToBuyGoodsIds")
    JsonResult removeAddMoneyToBuyGoodsIds(@RequestBody List<Long> addMoneyToBuyGoodsIds);
}
