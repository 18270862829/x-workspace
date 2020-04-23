package com.afiona.center.web.promotion.infrastructure.repo.impl;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import cn.hutool.core.collection.CollectionUtil;
import com.afiona.center.client.promotion.constants.PromotionType;
import com.afiona.center.client.promotion.constants.StatusType;
import com.afiona.center.client.promotion.constants.choice.SuitChoiceEnum;
import com.afiona.center.client.promotion.domain.model.suitrule.SuitGoods;
import com.afiona.center.client.promotion.domain.model.suitrule.SuitRule;
import com.afiona.center.client.promotion.model.CheckedCouponTemplate;
import com.afiona.center.client.promotion.model.query.CouponTemplateQuery;
import com.afiona.center.client.promotion.util.PageBeanUtils;
import com.afiona.center.web.promotion.domain.service.aggregate.IAggregate;
import com.afiona.center.web.promotion.domain.service.resolve.IBaseResolve;
import com.afiona.center.web.promotion.domain.service.resolve.IResolve;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.deepexi.util.pageHelper.PageBean;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.afiona.center.client.promotion.constants.PromotionResultEnum;
import com.afiona.center.client.promotion.domain.aggregate.CouponTemplateAggregate;
import com.afiona.center.client.promotion.domain.model.promotionrule.coupon.CouponTemplate;
import com.afiona.center.client.promotion.model.query.PromotionQuery;
import com.afiona.center.web.promotion.domain.repo.CouponTemplateRepository;
import com.afiona.center.web.promotion.domain.service.resolve.impl.BaseResolveService;
import com.afiona.center.web.promotion.infrastructure.dao.CouponTemplateDAO;
import com.afiona.center.web.promotion.infrastructure.dao.PromotionDAO;
import com.afiona.center.web.promotion.infrastructure.model.CouponTemplateDO;
import com.afiona.center.web.promotion.infrastructure.model.PromotionDO;
import com.afiona.common.pojo.JsonResult;
import com.afiona.common.util.CloneUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.hutool.core.bean.BeanUtil;

/**
 * 优惠劵模版repo实现
 *
 * @author dengweiyi
 * @date 2020-02-09
 */
@Repository
@Transactional(rollbackFor =Throwable.class)
public class CouponTemplateRepositoryImpl implements CouponTemplateRepository {

    @Resource
    private PromotionDAO promotionDAO;

    @Resource
    private CouponTemplateDAO couponTemplateDAO;

    @Resource
    private IResolve<CouponTemplateAggregate> iResolve;

    @Resource
    private IAggregate<CouponTemplateAggregate> iAggregate;

    @Resource
    private IBaseResolve<CouponTemplateAggregate> iBaseResolve;

    @Override
    public CouponTemplateAggregate get(long promotionId) {
        PromotionDO promotionDO = promotionDAO.getById(promotionId);
        if(promotionDO==null){
            return null;
        }
        List<CouponTemplateAggregate> promotions = CloneUtil.cloneList(Lists.newArrayList(promotionDO), CouponTemplateAggregate.class);
        iAggregate.aggregate(promotions);
        return promotions.get(0);
    }

    @Override
    public PageBean<CouponTemplateAggregate> list(PromotionQuery query) {
        query.setPromotionTypes(Lists.newArrayList(PromotionType.COUPON));
        PageBean<PromotionDO> pageBean = promotionDAO.listPage(query);
        List<CouponTemplateAggregate> promotions = CloneUtil.cloneList(pageBean.getContent(),
                CouponTemplateAggregate.class);
        iAggregate.aggregate(promotions);
        return PageBeanUtils.pageBeanCopy(promotions,pageBean);
    }

    @Override
    public List<CouponTemplateAggregate> listAll(PromotionQuery query) {
        query.setPromotionTypes(Lists.newArrayList(PromotionType.COUPON));
        List<PromotionDO> list = promotionDAO.listAll(query);
        List<CouponTemplateAggregate> promotions = CloneUtil.cloneList(list,
                CouponTemplateAggregate.class);
        iAggregate.aggregate(promotions);
        return promotions;
    }

    @Override
    public JsonResult store(CouponTemplateAggregate couponTemplateAggregate) {
        couponTemplateAggregate.setActivityId(-1L);
        couponTemplateAggregate.setType(PromotionType.COUPON);
        couponTemplateAggregate.setStatus(StatusType.HAS_NOT_STARTED);
        iResolve.resolve(couponTemplateAggregate);
        return JsonResult.create();
    }

    @Override
    public JsonResult remove(Long id) {
        return removeBatch(Lists.newArrayList(id));
    }

    @Override
    public JsonResult removeBatch(List<Long> ids) {
        if(ids.isEmpty()){
            JsonResult.create(PromotionResultEnum.NOT_EXIST);
        }
        iBaseResolve.removeBatchPromotion(ids);
        couponTemplateDAO.remove(new QueryWrapper<CouponTemplateDO>().in("promotion_id",ids));
        return JsonResult.create();
    }

    @Override
    public JsonResult updateCouponTemplate(CouponTemplate couponTemplate) {
        CouponTemplateDO couponTemplateDO = CloneUtil.clone(couponTemplate, CouponTemplateDO.class);
        BeanUtil.copyProperties(couponTemplate.getCouponUseTime(),couponTemplateDO);
        BeanUtil.copyProperties(couponTemplate.getCouponDistribute(),couponTemplateDO);

        int oldVersion = couponTemplateDO.getVersion();
        couponTemplateDO.setVersion(couponTemplateDO.getVersion()+1);
        boolean isUpdate = couponTemplateDAO.update(couponTemplateDO, new LambdaQueryWrapper<CouponTemplateDO>().eq(CouponTemplateDO::getId, couponTemplateDO.getId()).eq(CouponTemplateDO::getVersion, oldVersion));
        return JsonResult.create(isUpdate);
    }

    @Override
    public List<CouponTemplateAggregate> list(List<Long> templateIds) {
        if(CollectionUtil.isEmpty(templateIds)){
            return null;
        }
        Collection<CouponTemplateDO> dos = couponTemplateDAO.listByIds(templateIds);
        if(CollectionUtil.isEmpty(dos)){
            return null;
        }
        List<Long> promotionIds = dos.stream().map(CouponTemplateDO::getPromotionId).collect(Collectors.toList());
        PromotionQuery promotionQuery = new PromotionQuery();
        promotionQuery.setPromotionIds(promotionIds);
        return this.list(promotionQuery).getContent();
    }

    @Override
    public List<CheckedCouponTemplate> checkAndList(CouponTemplateQuery query) {
        if(CollectionUtil.isEmpty(query.getCouponTemplateIds()) || CollectionUtil.isEmpty(query.getSkuIds())){
            return Lists.newArrayList();
        }
        List<CheckedCouponTemplate> checkedCouponTemplates = Lists.newArrayList();
        List<CouponTemplateAggregate> couponTemplateAggregates = list(query.getCouponTemplateIds());
        for(CouponTemplateAggregate aggregate : couponTemplateAggregates){
            CouponTemplate couponTemplate = aggregate.getCouponTemplate();
            if(couponTemplate == null){
                continue;
            }
            SuitRule suitRule = aggregate.getSuitRule();
            if(suitRule == null){
                continue;
            }
            String reason = null;
            BigDecimal calcPreferentialAmount = null;
            boolean available = checkSuitRule(suitRule, query.getSkuIds());
            if(available){
                BigDecimal price = query.getCurrentTotalAmount();
                if(couponTemplate.achieveThreshold(price)){
                    calcPreferentialAmount = couponTemplate.calcPreferentialAmount(price);
                }else{
                    reason = "未达到优惠券门槛";
                }
            }else{
                reason = "部分商品不适用该优惠券";
            }

            CheckedCouponTemplate checkedCoupon = CloneUtil.clone(aggregate, CheckedCouponTemplate.class);
            checkedCoupon.setReason(reason);
            checkedCoupon.setPreferentialAmount(calcPreferentialAmount);
            checkedCouponTemplates.add(checkedCoupon);
        }
        return checkedCouponTemplates;
    }

    private static boolean checkSuitRule(SuitRule suitRule, List<Long> skuIds){
        if(suitRule == null){
            return false;
        }
        List<SuitGoods> suitGoods = suitRule.getSuitGoods();
        SuitChoiceEnum suitGoodsChoice = suitRule.getSuitGoodsChoice();
        switch (suitGoodsChoice){
            case ALL:
                return true;
            case SUIT:
                return contains(suitGoods, skuIds);
            case NOT_SUIT:
                return !contains(suitGoods, skuIds);
            default:
                return false;
        }
    }

    private static boolean contains(List<SuitGoods> suitGoods, List<Long> skuIds){
        if(CollectionUtil.isEmpty(suitGoods) || CollectionUtil.isEmpty(skuIds)){
            return false;
        }
        for(SuitGoods goods : suitGoods){
            if(skuIds.contains(goods.getSkuId())){
                return true;
            }
        }
        return false;
    }
}
