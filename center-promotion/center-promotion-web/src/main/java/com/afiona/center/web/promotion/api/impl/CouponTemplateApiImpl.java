package com.afiona.center.web.promotion.api.impl;

import com.afiona.center.client.promotion.api.CouponTemplateApi;
import com.afiona.center.client.promotion.domain.aggregate.CouponTemplateAggregate;
import com.afiona.center.client.promotion.domain.model.promotionrule.coupon.CouponTemplate;
import com.afiona.center.client.promotion.model.CheckedCouponTemplate;
import com.afiona.center.client.promotion.model.query.CouponTemplateQuery;
import com.afiona.center.client.promotion.model.query.PromotionQuery;
import com.afiona.center.web.promotion.domain.repo.CouponTemplateRepository;
import com.afiona.center.web.promotion.domain.service.applicability.CouponApplicability;
import com.afiona.common.pojo.JsonResult;
import com.deepexi.util.pageHelper.PageBean;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 优惠劵促销活动管理实现
 *
 * @author dengweiyi
 * @date 2020-02-10
 */
@RestController
public class CouponTemplateApiImpl implements CouponTemplateApi {

    @Resource
    private CouponTemplateRepository couponTemplateRepository;

    @Override
    public JsonResult<CouponTemplateAggregate> get(@RequestParam("promotionId") long promotionId) {
        return JsonResult.create(couponTemplateRepository.get(promotionId));
    }

    @Override
    public JsonResult<PageBean<CouponTemplateAggregate>> list(@RequestBody PromotionQuery query) {
        return JsonResult.create(couponTemplateRepository.list(query));
    }

    @Override
    public JsonResult<List<CouponTemplateAggregate>> listAll(@RequestBody PromotionQuery query) {
        return JsonResult.create(couponTemplateRepository.listAll(query));
    }

    @Override
    public JsonResult<List<CouponTemplateAggregate>> list(@RequestBody List<Long> templateIds) {
        return JsonResult.create(couponTemplateRepository.list(templateIds));
    }

    @Override
    public JsonResult store(@RequestBody CouponTemplateAggregate couponTemplateAggregate) {
        return couponTemplateRepository.store(couponTemplateAggregate);
    }

    @Override
    public JsonResult remove(@RequestBody Long id) {
        return couponTemplateRepository.remove(id);
    }

    @Override
    public JsonResult removeBatch(@RequestBody List<Long> ids) {
        return couponTemplateRepository.removeBatch(ids);
    }

    @Override
    public JsonResult updateCouponTemplate(@RequestBody CouponTemplate couponTemplate) {
        return couponTemplateRepository.updateCouponTemplate(couponTemplate);
    }

    @Override
    public JsonResult<List<CheckedCouponTemplate>> checkAndList(@RequestBody CouponTemplateQuery query) {
        return JsonResult.create(couponTemplateRepository.checkAndList(query));
    }

}