package com.afiona.center.client.promotion.api;

import com.afiona.center.client.promotion.domain.model.promotionrule.coupon.GetCouponCentre;
import com.afiona.center.client.promotion.model.query.GetCouponCentreQuery;
import com.afiona.common.pojo.JsonResult;
import com.deepexi.util.pageHelper.PageBean;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 领券中心管理
 *
 * @author LiJinXing
 * @date 2020/3/5
 */
@RequestMapping("/center/v1/get/coupon/centre")
@Api(value = "领券中心", tags = "领券中心")
public interface GetCouponCentreApi {

    /**
     * 查询
     *
     * @param id 主键
     * @return 领取中心信息
     */
    @GetMapping("/get/id")
    JsonResult<GetCouponCentre> get(@RequestParam("id") Long id);

    /**
     * 列表查询
     *
     * @param query 查询条件
     * @return 领券中心列表
     */
    @PostMapping("/pageListByMemberId/query")
    JsonResult<PageBean<GetCouponCentre>> pageListByMemberId(@RequestBody GetCouponCentreQuery query);

    /**
     * 保存
     *
     * @param getCouponCentre 领券中心信息
     * @return JsonResult
     */
    @PostMapping("/store/getCouponCentre")
    JsonResult store(@RequestBody GetCouponCentre getCouponCentre);

    /**
     * 保存
     *
     * @param list 领券中心信息列表
     * @return JsonResult
     */
    @PostMapping("/storeBatch/list")
    JsonResult storeBatch(@RequestBody List<GetCouponCentre> list);

    /**
     * 保存
     *
     * @param ids 主键列表
     * @return JsonResult
     */
    @DeleteMapping("/removeBatch/ids")
    JsonResult removeBatch(@RequestBody List<Long> ids);
}
