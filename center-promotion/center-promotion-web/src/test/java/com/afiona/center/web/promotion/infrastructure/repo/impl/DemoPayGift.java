package com.afiona.center.web.promotion.infrastructure.repo.impl;

import com.afiona.center.client.promotion.api.PromotionApi;
import com.afiona.center.client.promotion.model.Goods;
import com.afiona.center.client.promotion.model.PayGiftBenefit;
import com.afiona.center.client.promotion.model.query.PayGiftBenefitCalcQuery;
import com.afiona.center.web.promotion.Application;
import com.afiona.common.pojo.JsonResult;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.beans.Encoder;
import java.math.BigDecimal;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 * 支付有礼测试
 *
 * @author LiJinXing
 * @date 2020/3/24
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = Application.class)
public class DemoPayGift {

    @Resource
    private PromotionApi promotionApi;

    @Test
    public void testPayGiftBenefit(){
        PayGiftBenefitCalcQuery query =new PayGiftBenefitCalcQuery();
        Goods goods1 = new Goods().setSkuId(1L).setPrice(new BigDecimal(200));
        Goods goods2 = new Goods().setSkuId(2L).setPrice(new BigDecimal(400));
        query.setChannelId(1L).setMemberId(1L).setGoodsList(Lists.newArrayList(goods1,goods2));
        JsonResult<List<PayGiftBenefit>> result = promotionApi.payGiftBenefit(query);
        List<PayGiftBenefit> payGiftBenefits = result.getData();
        System.out.println(payGiftBenefits);
    }

    public static void main(String[] args) {

    }
}
