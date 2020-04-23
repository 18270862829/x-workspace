package com.afiona.center.web.promotion.infrastructure.repo.impl;

import com.afiona.center.client.promotion.constants.PreferentialType;
import com.afiona.center.client.promotion.constants.choice.*;
import com.afiona.center.client.promotion.domain.aggregate.FullReductionAggregate;
import com.afiona.center.client.promotion.domain.model.promotionrule.fullreduction.FullReductionRule;
import com.afiona.center.client.promotion.domain.model.promotionrule.fullreduction.FullReductionStairRule;
import com.afiona.center.client.promotion.domain.model.suitrule.SuitRule;
import com.afiona.center.client.promotion.model.PromotionBenefits;
import com.afiona.center.client.promotion.model.query.BenefitCalcQuery;
import com.afiona.center.web.promotion.Application;
import com.afiona.center.web.promotion.domain.repo.FullReductionRepository;
import com.afiona.center.web.promotion.domain.service.benefit.impl.FullReductionBenefitCalc;
import com.afiona.center.client.promotion.util.JsonUtils;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 满减福利测试
 *
 * @author LiJinXing
 * @date 2020/3/24
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = Application.class)
public class DemoFullTests {

    @Resource
    private FullReductionRepository fullReductionRepository;

    private List<BenefitCalcQuery.RelatedGoods> relatedGoodsList=new ArrayList<>();
    private FullReductionAggregate fullReductionAggregate;

    @Before
    public void before(){
        List<BenefitCalcQuery.RelatedGoods> list =new ArrayList<>();
        for (int i = 0; i <6 ; i++) {
            BenefitCalcQuery.RelatedGoods relatedGoods =new BenefitCalcQuery.RelatedGoods().setSkuId(201).setPrice(new BigDecimal(100));
            list.add(relatedGoods);
        }
        relatedGoodsList.addAll(list);
        fullReductionAggregate=fullReductionRepository.get(324L);
    }

    @Test
    public void testCalc(){

      }

    @Test
    public void insert() {
        FullReductionAggregate fullReductionAggregate = (FullReductionAggregate) new FullReductionAggregate().setSuitRule(new SuitRule().setSuitGoodsChoice(SuitChoiceEnum.ALL).setSuitMemberChoice(SuitChoiceEnum.ALL).setSuitChannelChoice(SuitChoiceEnum.ALL)).setName("满减折扣测试");

        FullReductionStairRule.PreferentialContent preferentialContent1 = new FullReductionStairRule.PreferentialContent().setAmount(new FullReductionStairRule.Amount().setAmountChoice(AmountChoiceEnum.DISCOUNT).setDiscount(new BigDecimal("0.8")));
        preferentialContent1.setContentTypeChoices(Lists.newArrayList(ContentTypeChoiceEnum.AMOUNT,ContentTypeChoiceEnum.GIFT,ContentTypeChoiceEnum.FREE_SHIPPING));
        preferentialContent1.setGift(new FullReductionStairRule.Gift().setGiftChoice(GiftChoiceEnum.SCORE).setScore(50));
        FullReductionStairRule fullReductionStairRule1 =new FullReductionStairRule().setLevel(1).setPreferentialThreshold(new FullReductionStairRule.PreferentialThreshold().setThresholdChoice(ThresholdChoiceEnum.NUM).setFullNum(5).setFullNumChoice(FullNumChoiceEnum.ALL)).setPreferentialContent(preferentialContent1);

        FullReductionStairRule.PreferentialContent preferentialContent2 = new FullReductionStairRule.PreferentialContent().setAmount(new FullReductionStairRule.Amount().setAmountChoice(AmountChoiceEnum.DISCOUNT).setDiscount(new BigDecimal("0.5")));
        preferentialContent2.setContentTypeChoices(Lists.newArrayList(ContentTypeChoiceEnum.AMOUNT,ContentTypeChoiceEnum.GIFT,ContentTypeChoiceEnum.FREE_SHIPPING));
        preferentialContent2.setGift(new FullReductionStairRule.Gift().setGiftChoice(GiftChoiceEnum.SCORE).setScore(99));
        FullReductionStairRule fullReductionStairRule2 =new FullReductionStairRule().setLevel(2).setPreferentialThreshold(new FullReductionStairRule.PreferentialThreshold().setThresholdChoice(ThresholdChoiceEnum.NUM).setFullNum(10).setFullNumChoice(FullNumChoiceEnum.ALL)).setPreferentialContent(preferentialContent2);

        FullReductionRule fullReductionRule = new FullReductionRule().setPreferentialType(PreferentialType.STAIR).setFullReductionStairRules(Lists.newArrayList(fullReductionStairRule1,fullReductionStairRule2));
        fullReductionAggregate.setFullReductionRule(fullReductionRule);
        System.out.println(JsonUtils.objToJson(fullReductionAggregate));
        fullReductionRepository.store(fullReductionAggregate);
    }

}
