package com.afiona.center.web.promotion.infrastructure.repo.impl;

import com.afiona.center.client.promotion.api.PromotionApi;
import com.afiona.center.client.promotion.domain.model.Promotion;
import com.afiona.center.client.promotion.model.MaterialBenefits;
import com.afiona.center.client.promotion.model.PromotionGroup;
import com.afiona.center.client.promotion.model.query.BenefitCalcQuery;
import com.afiona.center.web.promotion.Application;
import com.afiona.center.web.promotion.domain.service.promotion.PromotionDetailsService;
import com.afiona.common.pojo.JsonResult;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * 活动相关测试
 *
 * @author LiJinXing
 * @date 2020/3/29
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = Application.class)
public class DemoPromotion {

    @Resource
    private PromotionApi promotionApi;

    @Resource
    private PromotionDetailsService promotionDetailsService;

    @Test
    public void testPromotionListBySkuId(){
        List<Promotion> promotionList = promotionApi.promotionListBySkuId(201L).getData();
        System.out.println(promotionList);
    }

    @Test
    public void testSuitGoods(){
        Set<Long> ids = promotionDetailsService.listSuitGoodsBySkuIds(Sets.newHashSet(100L));
        System.out.println(ids);
    }

    @Test
    public void testPreferentialCalculation(){

        BenefitCalcQuery.InvolvedPromotion involvedPromotion1 = new BenefitCalcQuery.InvolvedPromotion();
        involvedPromotion1.setPromotionId(356L);
        BenefitCalcQuery.RelatedGoods relatedGoods1 = new BenefitCalcQuery.RelatedGoods().setSkuId(1001L).setSinglePromotionId(354L).setPrice(new BigDecimal(200)).setNum(1);
        involvedPromotion1.setRelatedGoods(Lists.newArrayList(relatedGoods1,relatedGoods1));

        BenefitCalcQuery.InvolvedPromotion involvedPromotion2 = new BenefitCalcQuery.InvolvedPromotion();
        involvedPromotion2.setPromotionId(357L);
        BenefitCalcQuery.RelatedGoods relatedGoods2 = new BenefitCalcQuery.RelatedGoods().setSkuId(1002L).setSinglePromotionId(355L).setPrice(new BigDecimal(200)).setNum(1);
        involvedPromotion2.setRelatedGoods(Lists.newArrayList(relatedGoods2,relatedGoods2));

        BenefitCalcQuery query = new BenefitCalcQuery().setChannelId(100).setMemberId(100).setInvolvedPromotions(Lists.newArrayList(involvedPromotion1,involvedPromotion2));

        long start = System.currentTimeMillis();
        JsonResult<MaterialBenefits> result = promotionApi.preferentialCalculation(query);
        MaterialBenefits materialBenefits = result.getData();
        long end = System.currentTimeMillis();
        System.out.println(end-start);
        System.out.println(materialBenefits);
    }

    @Test
    public void testListGroupBySkuIds(){
        long start = System.currentTimeMillis();
        JsonResult<List<PromotionGroup>> result = promotionApi.listGroupBySkuIds(Lists.newArrayList(411L));
        List<PromotionGroup> data = result.getData();
        long end = System.currentTimeMillis();
        System.out.println(end-start);
        System.out.println(data);


        String s ="MaterialBenefits(benefitList=[Benefit(promotion=Promotion(activityId=2, encoding=string, name=流程1-满减测试, activityName=null, purpose=string, type=FULL_REDUCTION, enabled=true, status=PROCESSING, startTime=Tue Apr 07 00:00:00 CST 2020, endTime=Wed Apr 07 00:00:00 CST 2021, description=满减, posterUrl=string, priority=0), singleBenefitList=[], preferentialAmount=400.00, score=0, couponTemplateIdList=null, giftSkuIds=null, freeShipping=false, discountsNumberEnum=null, addBuyGoodsList=null)])\n";
    }


    @Test
    public void testOptimalPromotion(){
        JsonResult<Promotion> result = promotionApi.optimalPromotion(0L);
        System.out.println(result.getData());
    }


}
