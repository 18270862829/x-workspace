package com.afiona.center.web.promotion.domain.service.applicability;

import com.afiona.center.client.promotion.constants.CouponType;
import com.afiona.center.client.promotion.constants.choice.SuitChoiceEnum;
import com.afiona.center.client.promotion.domain.aggregate.CouponTemplateAggregate;
import com.afiona.center.client.promotion.domain.model.promotionrule.coupon.CouponTemplate;
import com.afiona.center.client.promotion.domain.model.suitrule.SuitGoods;
import com.afiona.center.client.promotion.domain.model.suitrule.SuitRule;
import com.afiona.center.client.promotion.model.CouponApplicabilityList;
import com.afiona.center.client.promotion.model.query.CouponTemplateQuery;
import com.afiona.center.web.promotion.domain.repo.CouponTemplateRepository;
import com.afiona.center.web.promotion.domain.service.benefit.verify.SuitRuleVerify;
import com.afiona.common.pojo.JsonResult;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 优惠券券适用校验，福利 计算
 *
 * @author LiJinXing
 * @date 2020/3/9
 */
@Component
public class CouponApplicability {

    @Resource
    private CouponTemplateRepository couponTemplateRepository;

    @Deprecated
    public JsonResult<CouponApplicabilityList> checkApplicability(CouponTemplateQuery query){
        //不可用列表
        List<CouponApplicabilityList.NoApplicable> noApplicableList =new ArrayList<>();
        //可用列表
        List<CouponApplicabilityList.Applicable> applicableList =new ArrayList<>();
        List<CouponTemplateAggregate> couponTemplateAggregates = couponTemplateRepository.list(query.getCouponTemplateIds());
        for (CouponTemplateAggregate couponTemplateAggregate : couponTemplateAggregates) {
            //适用规则校验
            SuitRule suitRule = couponTemplateAggregate.getSuitRule();
            CouponApplicabilityList.NoApplicable noSuitGoods = new CouponApplicabilityList.NoApplicable().setPromotion(couponTemplateAggregate).setReason("未达到优惠券门槛").setSkuIds(new ArrayList<>());
            //商品校验-指定商品参与
            suitGoodsVerify(query, suitRule, noSuitGoods);

            //包含不适用商品
            if(!noSuitGoods.getSkuIds().isEmpty()){
                noApplicableList.add(noSuitGoods.setReason("部分商品不适用该优惠券"));
                continue;
            }

            if(!query.getSkuIds().isEmpty()){
                //门槛校验
                //订单中适用商品总价
                BigDecimal totalPrices = query.getCurrentTotalAmount();
                //订单中适用商品列表
                List<Long> skuIds = query.getSkuIds();
                if(totalPrices.compareTo(couponTemplateAggregate.getCouponTemplate().getUseThreshold())<0){
                    //订单中适用商品总价小于门槛价
                    CouponApplicabilityList.NoApplicable noApplicable = new CouponApplicabilityList.NoApplicable().setPromotion(couponTemplateAggregate).setReason("未能达到满"+couponTemplateAggregate.getCouponTemplate().getUseThreshold()+"的门槛");
                    noApplicable.setSkuIds(skuIds);
                    noApplicableList.add(noApplicable);
                }else {
                    //满足适用门槛-计算优惠
                    CouponTemplate couponTemplate = couponTemplateAggregate.getCouponTemplate();
                    //优惠券类型
                    CouponType couponType = couponTemplate.getCouponType();
                    //折扣券
                    if(couponType.equals(CouponType.DISCOUNT)){
                        //计算折扣金额
                        BigDecimal preferentialAmount =totalPrices.subtract(totalPrices.multiply(couponTemplate.getDiscount()));
                        CouponApplicabilityList.Applicable applicable =new   CouponApplicabilityList.Applicable().setPromotion(couponTemplateAggregate).setSkuIds(skuIds).setPreferentialAmount(preferentialAmount);
                        applicableList.add(applicable);
                    }
                    //代金券
                    else if (couponType.equals(CouponType.VOUCHER)){
                        //计算满减金额
                        BigDecimal preferentialAmount =couponTemplate.getReduceAmount();
                        CouponApplicabilityList.Applicable applicable =new   CouponApplicabilityList.Applicable().setPromotion(couponTemplateAggregate).setSkuIds(skuIds).setPreferentialAmount(preferentialAmount);
                        applicableList.add(applicable);
                    }
                }
            }else {
                noApplicableList.add(noSuitGoods);
            }
        }
        //填充数据
        CouponApplicabilityList couponApplicabilityList = new CouponApplicabilityList();
        //排序
        Collections.sort(applicableList);
        couponApplicabilityList.setApplicableList(applicableList);
        couponApplicabilityList.setNoApplicableList(noApplicableList);

        return JsonResult.create(couponApplicabilityList);
    }

    private void suitGoodsVerify(CouponTemplateQuery query, SuitRule suitRule, CouponApplicabilityList.NoApplicable noSuitGoods) {
        //订单中的商品列表
        List<Long> relatedGoods = query.getSkuIds();
        if(suitRule.getSuitGoodsChoice().equals(SuitChoiceEnum.SUIT)){
            //适用商品列表
            Set<Long> couponSuitGoods = suitRule.getSuitGoods().stream().map(SuitGoods::getSkuId).collect(Collectors.toSet());
            //填充数据
            for (Long skuId : relatedGoods) {
                if(!couponSuitGoods.contains(skuId)){
                    noSuitGoods.getSkuIds().add(skuId);
                }
            }
        }
        //商品校验-指定商品不参与
        else if(suitRule.getSuitGoodsChoice().equals(SuitChoiceEnum.NOT_SUIT)){
            //适用商品列表
            Set<Long> couponSuitGoods = suitRule.getSuitGoods().stream().map(SuitGoods::getSkuId).collect(Collectors.toSet());
            //填充数据
            for (Long skuId : relatedGoods) {
                if(couponSuitGoods.contains(skuId)){
                    noSuitGoods.getSkuIds().add(skuId);
                }
            }
        }
        //商品校验-所有商品适用
        else if(!suitRule.getSuitGoodsChoice().equals(SuitChoiceEnum.ALL)){
            noSuitGoods.getSkuIds().addAll(query.getSkuIds());
        }
    }
}
