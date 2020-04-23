package com.afiona.center.web.promotion.domain.service.benefit.impl;

import com.afiona.center.client.promotion.constants.PromotionResultEnum;
import com.afiona.center.client.promotion.constants.choice.GiftChoiceEnum;
import com.afiona.center.client.promotion.constants.choice.GoodsThresholdRuleTypeEnum;
import com.afiona.center.client.promotion.constants.choice.PayGiftTypeEnum;
import com.afiona.center.client.promotion.domain.aggregate.PayGiftAggregate;
import com.afiona.center.client.promotion.domain.model.Promotion;
import com.afiona.center.client.promotion.domain.model.promotionrule.paygift.PayGiftCoupon;
import com.afiona.center.client.promotion.domain.model.promotionrule.paygift.PayGiftGoods;
import com.afiona.center.client.promotion.domain.model.promotionrule.paygift.PayGiftRule;
import com.afiona.center.client.promotion.domain.model.promotionrule.paygift.PayGiftStairRule;
import com.afiona.center.client.promotion.model.PayGiftBenefit;
import com.afiona.center.client.promotion.model.query.BenefitCalcQuery;
import com.afiona.center.web.promotion.domain.service.aggregate.impl.PayGiftAggregateService;
import com.afiona.common.pojo.JsonResult;
import com.afiona.common.util.CloneUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 支付有礼促销福利计算
 *
 * @author LiJinXing
 * @date 2020/2/26
 */
@Component
public class PayGiftApiBenefitCalc  {

    @Resource
    private PayGiftAggregateService payGiftAggregateService;

    public JsonResult<PayGiftBenefit> calcBenefits(Promotion promotion, BenefitCalcQuery.InvolvedPromotion involvedPromotion) {
        PayGiftAggregate payGiftAggregate = CloneUtil.clone(promotion, PayGiftAggregate.class);
        payGiftAggregateService.aggregate(Lists.newArrayList(payGiftAggregate));
        return getBenefitsJsonResult(involvedPromotion.getRelatedGoods(), payGiftAggregate);
    }

    public JsonResult<PayGiftBenefit> getBenefitsJsonResult(List<BenefitCalcQuery.RelatedGoods> relatedGoods, PayGiftAggregate payGiftAggregate) {
        PayGiftRule payGiftRule = payGiftAggregate.getPayGiftRule();
        if(payGiftRule==null){
            return JsonResult.create(PromotionResultEnum.DATA_ERROR);
        }
        //商品支付 总价
        BigDecimal goodsTotalPrice = relatedGoods.stream()
                .map(BenefitCalcQuery.RelatedGoods::getPrice).reduce((p1, p2) -> p1.add(p2)).get();
        //循环折扣
        if(payGiftRule.getPayGiftType().equals(PayGiftTypeEnum.LOOP)){
            return getLoopPayGiftBenefitJsonResult(relatedGoods, payGiftRule, goodsTotalPrice);
        }
        //阶梯折扣
        else if(payGiftRule.getPayGiftType().equals(PayGiftTypeEnum.STAIR)){
            return getStairPayGiftBenefitJsonResult(relatedGoods, payGiftRule, goodsTotalPrice);
        }
        //未满足任何门槛
        return JsonResult.create();
    }

    private JsonResult<PayGiftBenefit> getStairPayGiftBenefitJsonResult(List<BenefitCalcQuery.RelatedGoods> relatedGoods, PayGiftRule payGiftRule, BigDecimal goodsTotalPrice) {
        //对优惠信息按等级排序
        List<PayGiftStairRule> payGiftStairRules = payGiftRule.getPayGiftStairRules();
        //排序
        Collections.sort(payGiftStairRules);
        Integer fullGoods = payGiftStairRules.get(0).getPreferentialThreshold().getFullGoods();
        //记录礼品
        PayGiftBenefit payGiftBenefit=null;
        if(fullGoods==null||fullGoods.equals(0)){
            //满x元 享受优惠
            //按优惠等级靠考虑
            for (PayGiftStairRule payGiftStairRule : payGiftStairRules) {
                BigDecimal oddPrice= goodsTotalPrice.subtract(payGiftStairRule.getPreferentialThreshold().getFullOrder());
                if(oddPrice.compareTo(new BigDecimal("0"))>=0){
                    //设置礼物
                    payGiftBenefit=gift(1,payGiftStairRule);
                }else {
                    break;
                }
            }
            return JsonResult.create(payGiftBenefit);
        }else {
            //满x件 享受优惠 按优惠等级考虑
            //商品总数
            int totalNum = relatedGoods.size();
            //相同的商品情况
            Map<Long, Integer> map = sameGoods(relatedGoods);
            Set<Long> skuIdSet = map.keySet();
            //不同的商品
            int differentNum = skuIdSet.size();
            for (PayGiftStairRule payGiftStairRule : payGiftStairRules) {
                // 考虑商品类型
                GoodsThresholdRuleTypeEnum goodsThresholdRuleType = payGiftStairRule.getPreferentialThreshold().getGoodsThresholdRuleType();
                //满件门槛
                Integer fullNum = payGiftStairRule.getPreferentialThreshold().getFullGoods();
                if(goodsThresholdRuleType.equals(GoodsThresholdRuleTypeEnum.ALL_GOODS)){
                    //所有商品
                    int oddNum=totalNum-fullNum;
                    if(oddNum>=0){
                        payGiftBenefit=gift(1,payGiftStairRule);
                    }else {
                        break;
                    }
                }
                else if(goodsThresholdRuleType.equals(GoodsThresholdRuleTypeEnum.SECTIONAL_GOODS)){
                    //不同的商品
                    int oddNum=differentNum-fullNum;
                    if(oddNum>=0){
                        payGiftBenefit=gift(1,payGiftStairRule);
                    }else {
                        break;
                    }
                }
                else if(goodsThresholdRuleType.equals(GoodsThresholdRuleTypeEnum.SAME_GOODS)){
                    for (Long key : skuIdSet) {
                        if(map.get(key)-fullNum>=0){
                            payGiftBenefit=gift(1,payGiftStairRule);
                        }
                    }
                }
            }
            return JsonResult.create(payGiftBenefit);
        }
    }

    private JsonResult<PayGiftBenefit> getLoopPayGiftBenefitJsonResult(List<BenefitCalcQuery.RelatedGoods> relatedGoods, PayGiftRule payGiftRule, BigDecimal goodsTotalPrice) {
        PayGiftStairRule payGiftStairRule = payGiftRule.getPayGiftStairRules().get(0);
        //满x元
        Integer fullGoods = payGiftStairRule.getPreferentialThreshold().getFullGoods();
        if(fullGoods==null||fullGoods.equals(0)){
            BigDecimal fullOrder = payGiftStairRule.getPreferentialThreshold().getFullOrder();
            //循环数
            int num = goodsTotalPrice.divide(fullOrder).intValue();
            //返回数据
            return JsonResult.create(gift(num, payGiftStairRule));
        }
        else {
            //满x件 考虑商品类型
            GoodsThresholdRuleTypeEnum goodsThresholdRuleType = payGiftStairRule.getPreferentialThreshold().getGoodsThresholdRuleType();
            //满件门槛
            Integer fullNum = payGiftStairRule.getPreferentialThreshold().getFullGoods();
            if(goodsThresholdRuleType.equals(GoodsThresholdRuleTypeEnum.ALL_GOODS)){
                //所有商品，考虑循环次数
                int num = relatedGoods.size() / fullNum;
                //返回数据
                return JsonResult.create(gift(num, payGiftStairRule));
            }
            else if(goodsThresholdRuleType.equals(GoodsThresholdRuleTypeEnum.SECTIONAL_GOODS)){
                //不同的商品
               int num= Sets.newHashSet(relatedGoods).size() / fullNum;
               //返回数据
                return JsonResult.create(gift(num, payGiftStairRule));
            }
            else if(goodsThresholdRuleType.equals(GoodsThresholdRuleTypeEnum.SAME_GOODS)){
                //相同的商品 不同商品都满足条件 礼品叠加
                Map<Long, Integer> map = sameGoods(relatedGoods);
                //循环数
                int num =0;
                Collection<Integer> values = map.values();
                for (Integer i : values) {
                    num=num+i/fullNum;
                }
                return JsonResult.create(gift(num,payGiftStairRule));
            }
        }
        return null;
    }


    private Map<Long,Integer> sameGoods(List<BenefitCalcQuery.RelatedGoods> relatedGoods){
        Map<Long,Integer> map =new HashMap<>(16);
        List<Long> skuIds = relatedGoods.stream().map(BenefitCalcQuery.RelatedGoods::getSkuId).collect(Collectors.toList());
        //将skuId和对应的数量放入map集合
        for (Long skuId : skuIds) {
            Integer num = map.get(skuId);
            if(num==null||num.equals(0)){
                map.put(skuId,1);
            }else {
                map.put(skuId,num+1);
            }
        }
        return map;
    }

    private  PayGiftBenefit gift(int num,PayGiftStairRule payGiftStairRule){
        PayGiftBenefit benefit = new PayGiftBenefit();
        if(num>0){
            //赠送奖品，先查看 送礼选择
            List<GiftChoiceEnum> giftChoice = payGiftStairRule.getGiftChoice();
            if(giftChoice==null||giftChoice.isEmpty()){
                return null;
            }
            if(giftChoice.contains(GiftChoiceEnum.SCORE)){
                benefit.setScore(payGiftStairRule.getScore()*num);
            }
            if (giftChoice.contains(GiftChoiceEnum.COUPON)){
                List<Long> couponTemplateIds =new ArrayList<>();
                for (int i = 0; i <num ; i++) {
                    couponTemplateIds.addAll(payGiftStairRule.getPayGiftCoupons().stream().map(PayGiftCoupon::getCouponTemplateId).collect(Collectors.toList()));
                }
                benefit.setCouponTemplateIdList(couponTemplateIds);
            }
            if(giftChoice.contains(GiftChoiceEnum.GOODS)){
                List<Long> skuIds =new ArrayList<>();
                for (int i = 0; i <num ; i++) {
                    skuIds.addAll(payGiftStairRule.getPayGiftGoods().stream().map(PayGiftGoods::getSkuId).collect(Collectors.toList()));
                }
                benefit.setGiftSkuIds(skuIds);
            }
        }
        return benefit;
    }
}
