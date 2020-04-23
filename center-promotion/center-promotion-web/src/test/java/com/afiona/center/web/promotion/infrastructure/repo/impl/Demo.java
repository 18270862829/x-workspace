package com.afiona.center.web.promotion.infrastructure.repo.impl;

import com.afiona.center.client.promotion.constants.PromotionType;
import com.afiona.center.client.promotion.domain.aggregate.*;
import com.afiona.center.client.promotion.domain.model.IPromotion;
import com.afiona.center.client.promotion.domain.model.promotionrule.addmoneytobuy.AddMoneyToBuyRule;
import com.afiona.center.client.promotion.domain.model.promotionrule.coupon.CouponTemplate;
import com.afiona.center.client.promotion.domain.model.promotionrule.fixedprice.FixedPriceRule;
import com.afiona.center.client.promotion.domain.model.promotionrule.fullreduction.FullReductionRule;
import com.afiona.center.client.promotion.domain.model.promotionrule.paygift.PayGiftRule;
import com.afiona.center.client.promotion.domain.model.promotionrule.seckill.SecondsKillRule;
import com.afiona.center.client.promotion.domain.model.promotionrule.specialdiscount.SpecialDiscountRule;
import com.afiona.center.client.promotion.domain.model.promotionrule.suit.SuitGroupRule;
import com.afiona.center.client.promotion.model.query.PromotionQuery;
import com.afiona.center.web.promotion.Application;
import com.afiona.center.web.promotion.domain.repo.*;
import com.afiona.center.web.promotion.domain.service.aggregate.IAggregate;
import com.afiona.center.web.promotion.infrastructure.xxl.ActivityJob;
import com.afiona.center.web.promotion.infrastructure.xxl.PromotionJob;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.xxl.job.core.biz.model.ReturnT;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.util.*;

/**
 * 其他调试
 *
 * @author LiJinXing
 * @date 2020/4/9
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = Application.class)
public class Demo {

    @Resource
    private PromotionJob promotionJob;

    @Resource
    private ActivityJob activityJob;

    @Resource
    private AddMoneyToBuyRepository addMoneyToBuyRepository;

    @Resource
    private CouponTemplateRepository couponTemplateRepository;

    @Resource
    private FixedPriceRepository fixedPriceRepository;

    @Resource
    private FullReductionRepository fullReductionRepository;

    @Resource
    private SecondsKillRepository secondsKillRepository;

    @Resource
    private SpecialDiscountRepository specialDiscountRepository;

    @Resource
    private PayGiftRepository payGiftRepository;

    @Resource
    private SuitGroupRepository suitGroupRepository;

    private Map<BasePromotionRepository, Set<Long>> map =new HashMap<>();

    @Test
    public void testPromotionStatusUpdateJob(){
        ReturnT<String> returnT = promotionJob.promotionStatusUpdateJob();
        System.out.println(returnT);
    }

    @Test
    public void testActivityStatusUpdateJob(){
        ReturnT<String> returnT = activityJob.activityStatusUpdateJob();
        System.out.println(returnT);
    }


    /**
     * 请理脏数据
     */
    @Test
    public void dirtyData () {
        List<AddMoneyToBuyAggregate> addMoneyToBuyAggregates = addMoneyToBuyRepository.listAll(new PromotionQuery());
        for (AddMoneyToBuyAggregate addMoneyToBuyAggregate : addMoneyToBuyAggregates) {
            AddMoneyToBuyRule addMoneyToBuyRule = addMoneyToBuyAggregate.getAddMoneyToBuyRule();
            if(addMoneyToBuyRule==null||isError(addMoneyToBuyAggregate)){
                put(addMoneyToBuyRepository,addMoneyToBuyAggregate.getId());
            }
        }
        put(addMoneyToBuyRepository,0L);
        List<CouponTemplateAggregate> couponTemplateAggregates = couponTemplateRepository.listAll(new PromotionQuery());
        for (CouponTemplateAggregate couponTemplateAggregate : couponTemplateAggregates) {
            CouponTemplate couponTemplate = couponTemplateAggregate.getCouponTemplate();
            if(couponTemplate==null||isError(couponTemplateAggregate)){
                put(couponTemplateRepository,couponTemplateAggregate.getId());
            }
        }
        put(couponTemplateRepository,0L);
        List<FixedPriceAggregate> fixedPriceAggregates = fixedPriceRepository.listAll(new PromotionQuery());
        for (FixedPriceAggregate fixedPriceAggregate : fixedPriceAggregates) {
            FixedPriceRule fixedPriceRule = fixedPriceAggregate.getFixedPriceRule();
            if(fixedPriceRule==null||isError(fixedPriceAggregate)){
                put(fixedPriceRepository,fixedPriceAggregate.getId());
            }
        }
        put(fixedPriceRepository,0L);
        List<FullReductionAggregate> fullReductionAggregates = fullReductionRepository.listAll(new PromotionQuery());
        for (FullReductionAggregate fullReductionAggregate : fullReductionAggregates) {
            FullReductionRule fullReductionRule = fullReductionAggregate.getFullReductionRule();
            if(fullReductionRule==null||isError(fullReductionAggregate)){
                put(fullReductionRepository,fullReductionAggregate.getId());
            }
        }
        put(fullReductionRepository,0L);
        List<SecondsKillAggregate> secondsKillAggregates = secondsKillRepository.listAll(new PromotionQuery());
        for (SecondsKillAggregate secondsKillAggregate : secondsKillAggregates) {
            SecondsKillRule secondsKillRule = secondsKillAggregate.getSecondsKillRule();
            if(secondsKillRule==null||isError(secondsKillAggregate)){
                put(secondsKillRepository,secondsKillAggregate.getId());
            }
        }
        put(secondsKillRepository,0L);
        List<SpecialDiscountAggregate> specialDiscountAggregates = specialDiscountRepository.listAll(new PromotionQuery());
        for (SpecialDiscountAggregate specialDiscountAggregate : specialDiscountAggregates) {
            SpecialDiscountRule specialDiscountRule = specialDiscountAggregate.getSpecialDiscountRule();
            if(specialDiscountRule==null||isError(specialDiscountAggregate)){
                put(specialDiscountRepository,specialDiscountAggregate.getId());
            }
        }
        put(specialDiscountRepository,0L);
        List<PayGiftAggregate> payGiftAggregates = payGiftRepository.listAll(new PromotionQuery());
        for (PayGiftAggregate payGiftAggregate : payGiftAggregates) {
            PayGiftRule payGiftRule = payGiftAggregate.getPayGiftRule();
            if(payGiftRule==null||isError(payGiftAggregate)){
                put(payGiftRepository,payGiftAggregate.getId());
            }
        }
        put(payGiftRepository,0L);
        List<SuitGroupAggregate> suitGroupAggregates = suitGroupRepository.listAll(new PromotionQuery());
        for (SuitGroupAggregate suitGroupAggregate : suitGroupAggregates) {
            SuitGroupRule suitGroupRule = suitGroupAggregate.getSuitGroupRule();
            if(suitGroupRule==null||isError(suitGroupAggregate)){
                put(suitGroupRepository,suitGroupAggregate.getId());
            }
        }
        put(suitGroupRepository,0L);
        Set<Map.Entry<BasePromotionRepository, Set<Long>>> entrySet = map.entrySet();
        for (Map.Entry<BasePromotionRepository, Set<Long>> entry : entrySet) {
            BasePromotionRepository basePromotionRepository = entry.getKey();
            Set<Long> value = entry.getValue();
            value.add(0L);
            basePromotionRepository.removeBatch(Lists.newArrayList(value));
        }
    }
    private Boolean isError(IPromotion iPromotion){
        Long activityId = iPromotion.getActivityId();
        return activityId == null || activityId == 0;
    }

    private void put(BasePromotionRepository repository,Long id){
        Set<Long> set = map.get(repository);
        if(set==null){
            map.put(repository, Sets.newHashSet(id));
        }else {
            map.get(repository).add(id);
        }
    }



}
