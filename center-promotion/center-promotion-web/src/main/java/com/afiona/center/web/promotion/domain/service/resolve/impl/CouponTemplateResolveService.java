package com.afiona.center.web.promotion.domain.service.resolve.impl;

import cn.hutool.core.bean.BeanUtil;
import com.afiona.center.client.promotion.constants.PromotionResultEnum;
import com.afiona.center.client.promotion.domain.aggregate.AddMoneyToBuyAggregate;
import com.afiona.center.client.promotion.domain.aggregate.CouponTemplateAggregate;
import com.afiona.center.web.promotion.domain.service.resolve.IBaseResolve;
import com.afiona.center.web.promotion.domain.service.resolve.IResolve;
import com.afiona.center.web.promotion.infrastructure.dao.CouponTemplateDAO;
import com.afiona.center.web.promotion.infrastructure.model.CouponTemplateDO;
import com.afiona.common.pojo.JsonResult;
import com.afiona.common.util.CloneUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 优惠劵模版解析存储器
 *
 * @author dengweiyi
 * @date 2020-02-09
 */
@Service
public class CouponTemplateResolveService  implements IResolve<CouponTemplateAggregate> {

    @Resource
    private IBaseResolve<CouponTemplateAggregate> baseResolveService;

    @Resource
    private CouponTemplateDAO couponTemplateDAO;

    @Override
    public Long resolve(CouponTemplateAggregate couponTemplateAggregate){
        Long promotionId = baseResolveService.resolve(couponTemplateAggregate);
        if(promotionId==null){
            JsonResult.create(PromotionResultEnum.DATA_ERROR);
        }
        resolveCoupons(couponTemplateAggregate,promotionId);
        return promotionId;
    }

    private void resolveCoupons(CouponTemplateAggregate couponTemplateAggregate,Long promotionId){
        CouponTemplateDO couponTemplateDO = CloneUtil.clone(couponTemplateAggregate.getCouponTemplate(), CouponTemplateDO.class);
        couponTemplateDO.setStartTime(couponTemplateAggregate.getCouponTemplate().getCouponUseTime().getTimeRange().getStartTime());
        couponTemplateDO.setEndTime(couponTemplateAggregate.getCouponTemplate().getCouponUseTime().getTimeRange().getEndTime());
        couponTemplateDO.setPromotionId(promotionId);
        BeanUtil.copyProperties(couponTemplateAggregate.getCouponTemplate().getCouponDistribute(),couponTemplateDO);
        BeanUtil.copyProperties(couponTemplateAggregate.getCouponTemplate().getCouponUseTime(),couponTemplateDO);
        couponTemplateDAO.saveOrUpdate(couponTemplateDO);
    }
}