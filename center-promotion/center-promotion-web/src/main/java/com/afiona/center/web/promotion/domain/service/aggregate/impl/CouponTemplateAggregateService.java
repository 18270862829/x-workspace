package com.afiona.center.web.promotion.domain.service.aggregate.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.afiona.center.client.promotion.domain.aggregate.AddMoneyToBuyAggregate;
import com.afiona.center.client.promotion.domain.aggregate.CouponTemplateAggregate;
import com.afiona.center.client.promotion.domain.model.promotionrule.coupon.CouponTemplate;
import com.afiona.center.client.promotion.util.MergeUtils;
import com.afiona.center.client.promotion.util.RangeUtil;
import com.afiona.center.web.promotion.domain.service.aggregate.IAggregate;
import com.afiona.center.web.promotion.domain.service.aggregate.IBaseAggregate;
import com.afiona.center.web.promotion.infrastructure.dao.CouponTemplateDAO;
import com.afiona.center.web.promotion.infrastructure.model.CouponTemplateDO;
import com.afiona.common.util.CloneUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.afiona.center.client.promotion.util.GenericHelper.*;

/**
 * 优惠劵模版聚合器
 *
 * @author dengweiyi
 * @date 2020-02-09
 */
@Service
public class CouponTemplateAggregateService  implements IAggregate<CouponTemplateAggregate> {

    @Resource
    private CouponTemplateDAO couponTemplateDAO;

    @Resource
    private IBaseAggregate<CouponTemplateAggregate> baseAggregateService;

    @Override
    public void aggregate(List<CouponTemplateAggregate> promotions){
        if(com.deepexi.util.CollectionUtil.isEmpty(promotions)){
            return;
        }
        baseAggregateService.aggregate(promotions);
        aggregateCoupons(promotions);
    }

    private void aggregateCoupons(List<CouponTemplateAggregate> promotions){
        Map<Long, List<CouponTemplateDO>> couponTemplateDOMap = getDOMap(promotions, couponTemplateDAO);
        List<CouponTemplateDO> dos = MergeUtils.mergeList(couponTemplateDOMap.values());
        List<CouponTemplate> couponTemplates = CloneUtil.cloneList(dos,CouponTemplate.class);
        for(CouponTemplate couponTemplate : couponTemplates){
            List<CouponTemplateDO> couponTemplateDOList = couponTemplateDOMap.get(couponTemplate.getPromotionId());
            if(CollectionUtil.isEmpty(couponTemplateDOList)){
                continue;
            }
            for (CouponTemplateDO couponTemplateDO : couponTemplateDOList) {
                couponTemplate.setCouponUseTime(toCouponUseTime(couponTemplateDO));
                couponTemplate.setCouponDistribute(toCouponDistribute(couponTemplateDO));
            }

        }

        Map<Long, CouponTemplate> couponTemplateMap = couponTemplates.stream().collect(
                Collectors.toMap(CouponTemplate::getPromotionId, Function.identity()));
        for(CouponTemplateAggregate couponTemplateAggregate : promotions){
            CouponTemplate couponTemplate = couponTemplateMap.get(couponTemplateAggregate.getId());
            if(couponTemplate == null){
                continue;
            }
            couponTemplateAggregate.setCouponTemplate(couponTemplate);
        }
    }

    private CouponTemplate.CouponUseTime toCouponUseTime(CouponTemplateDO couponTemplateDO){
        CouponTemplate.CouponUseTime couponUseTime = new CouponTemplate.CouponUseTime();
        couponUseTime.setUseTimeChoice(couponTemplateDO.getUseTimeChoice());
        couponUseTime.setTimeRange(new RangeUtil.TimeRange(couponTemplateDO.getStartTime(), couponTemplateDO.getEndTime()));
        couponUseTime.setTheSameDayInterval(couponTemplateDO.getTheSameDayInterval());
        couponUseTime.setTheNextDayInterval(couponTemplateDO.getTheNextDayInterval());
        return couponUseTime;
    }

    private CouponTemplate.CouponDistribute toCouponDistribute(CouponTemplateDO couponTemplateDO){
        CouponTemplate.CouponDistribute couponDistribute = new CouponTemplate.CouponDistribute();
        couponDistribute.setDistributeChoice(couponTemplateDO.getDistributeChoice());
        couponDistribute.setTotal(couponTemplateDO.getTotal());
        couponDistribute.setSurplusNum(couponTemplateDO.getSurplusNum());
        return couponDistribute;
    }

}
