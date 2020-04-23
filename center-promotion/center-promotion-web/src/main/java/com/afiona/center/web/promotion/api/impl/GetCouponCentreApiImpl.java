package com.afiona.center.web.promotion.api.impl;

import java.util.List;

import javax.annotation.Resource;

import com.deepexi.util.pageHelper.PageBean;
import org.springframework.web.bind.annotation.RequestBody;

import com.afiona.center.client.promotion.api.GetCouponCentreApi;
import com.afiona.center.client.promotion.domain.model.promotionrule.coupon.GetCouponCentre;
import com.afiona.center.client.promotion.model.query.GetCouponCentreQuery;
import com.afiona.center.web.promotion.domain.repo.GetCouponCentreRepository;
import com.afiona.common.pojo.JsonResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 领券中心管理实现
 *
 * @author LiJinXing
 * @date 2020/3/5
 */
@RestController
public class GetCouponCentreApiImpl implements GetCouponCentreApi {

    @Resource
    private GetCouponCentreRepository getCouponCentreRepository;

    @Override
    public JsonResult<GetCouponCentre> get(@RequestParam("id") Long id) {
        return JsonResult.create(getCouponCentreRepository.get(id));
    }

    @Override
    public JsonResult<PageBean<GetCouponCentre>> pageListByMemberId(@RequestBody GetCouponCentreQuery query) {
        return JsonResult.create(getCouponCentreRepository.pageListByMemberId(query));
    }

    @Override
    public JsonResult store(@RequestBody GetCouponCentre getCouponCentre) {
        return getCouponCentreRepository.store(getCouponCentre);
    }

    @Override
    public JsonResult storeBatch(@RequestBody List<GetCouponCentre> list) {
        return getCouponCentreRepository.storeBatch(list);
    }

    @Override
    public JsonResult removeBatch(@RequestBody List<Long> ids) {
        return getCouponCentreRepository.removeBatch(ids);
    }
}
