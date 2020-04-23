package com.afiona.center.web.promotion.domain.repo;

import com.afiona.center.client.promotion.domain.aggregate.CouponTemplateAggregate;
import com.afiona.center.client.promotion.domain.model.promotionrule.coupon.CouponTemplate;
import com.afiona.center.client.promotion.model.CheckedCouponTemplate;
import com.afiona.center.client.promotion.model.query.CouponTemplateQuery;
import com.afiona.common.pojo.JsonResult;

import java.util.List;

/**
 * 优惠劵模版repo
 *
 * @author dengweiyi
 * @date 2020-02-09
 */
public interface CouponTemplateRepository extends BasePromotionRepository<CouponTemplateAggregate>{

    /**
     * 优惠券模板修改
     *
     * @param couponTemplate 优惠券模板
     * @return jsonResult
     */
    JsonResult updateCouponTemplate(CouponTemplate couponTemplate);

    /**
     * 根据优惠模板id列表查询优惠券详细信息
     *
     * @param templateIds 优惠券 id列表
     * @return 优惠些详细信息列表
     */
    List<CouponTemplateAggregate> list(List<Long> templateIds);

    /**
     * 校验并返回优惠劵模版列表
     *
     * @param query
     * @return : java.util.List<com.afiona.center.client.promotion.model.CheckedCouponTemplate>
     */
    List<CheckedCouponTemplate> checkAndList(CouponTemplateQuery query);
}
