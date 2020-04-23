package com.afiona.center.web.promotion.infrastructure.repo.impl;

import com.afiona.center.client.promotion.model.CouponApplicabilityList;
import com.afiona.center.client.promotion.model.query.CouponTemplateQuery;
import com.afiona.center.web.promotion.Application;
import com.afiona.center.web.promotion.domain.service.applicability.CouponApplicability;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = Application.class)
public class DemoCheckedCouponTemplateTests {

    @Resource
    private CouponApplicability couponApplicability;

    private CouponTemplateQuery query =new CouponTemplateQuery();

    @Before
    public void before(){
        query.setCouponTemplateIds(Lists.newArrayList(62L,63L));
        query.setSkuIds(Lists.newArrayList(100L,101L)).setCurrentTotalAmount(new BigDecimal(200));
    }

    @Test
    public void testCoupon(){
        CouponApplicabilityList data = couponApplicability.checkApplicability(query).getData();
        System.out.println(data);

    }


}