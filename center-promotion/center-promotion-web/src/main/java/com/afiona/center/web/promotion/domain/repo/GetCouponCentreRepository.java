package com.afiona.center.web.promotion.domain.repo;

import com.afiona.center.client.promotion.domain.model.promotionrule.coupon.GetCouponCentre;
import com.afiona.center.client.promotion.model.query.GetCouponCentreQuery;
import com.afiona.common.pojo.JsonResult;
import com.deepexi.util.pageHelper.PageBean;

import java.util.List;

/**
 * 领券中心repo
 *
 * @author LiJinXing
 * @date 2020/3/5
 */
public interface GetCouponCentreRepository {

    /**
     * 查询
     *
     * @param id 主键
     * @return 领取中心信息
     */
    GetCouponCentre get(Long id);

    /**
     * 列表查询
     *
     * @param query 查询条件
     * @return 领券中心列表
     */
    PageBean<GetCouponCentre> pageListByMemberId(GetCouponCentreQuery query);

    /**
     * 保存
     *
     * @param getCouponCentre 领券中心信息
     * @return JsonResult
     */
    JsonResult store(GetCouponCentre getCouponCentre);

    /**
     * 保存
     *
     * @param list 领券中心信息列表
     * @return JsonResult
     */
    JsonResult storeBatch(List<GetCouponCentre> list);

    /**
     * 保存
     *
     * @param ids 主键列表
     * @return JsonResult
     */
    JsonResult removeBatch(List<Long> ids);

}
