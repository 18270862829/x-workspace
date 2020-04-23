package com.afiona.center.web.promotion.domain.service.benefit.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.afiona.center.client.promotion.constants.PromotionResultEnum;
import com.afiona.center.client.promotion.domain.aggregate.FullReductionAggregate;
import com.afiona.center.client.promotion.domain.model.Promotion;
import com.afiona.center.client.promotion.domain.model.promotionrule.fullreduction.FullReductionRule;
import com.afiona.center.client.promotion.model.Benefit;
import com.afiona.center.client.promotion.model.Goods;
import com.afiona.center.client.promotion.model.MaterialBenefits;
import com.afiona.center.web.promotion.domain.service.aggregate.impl.FullReductionAggregateService;
import com.afiona.center.web.promotion.domain.service.benefit.MultipleBenefitCalc;
import com.afiona.center.web.promotion.domain.service.benefit.verify.SuitRuleVerify;
import com.afiona.center.web.promotion.util.FillUtils;
import com.afiona.common.pojo.JsonResult;
import com.afiona.common.util.CloneUtil;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 满减促销活动福利计算
 *
 * @author dengweiyi
 * @date 2020-02-11
 */
@Component
public class FullReductionBenefitCalc implements MultipleBenefitCalc {

    @Resource
    private FullReductionAggregateService fullReductionAggregateService;

    @Override
    public JsonResult calcBenefits(Promotion promotion, List<Goods> goodsList,MaterialBenefits materialBenefits) {
        FullReductionAggregate fullReductionAggregate = CloneUtil.clone(promotion, FullReductionAggregate.class);
        fullReductionAggregateService.aggregate(Lists.newArrayList(fullReductionAggregate));
        JsonResult<Benefit> result = getBenefitsJsonResult(goodsList, fullReductionAggregate);
        return FillUtils.fillResult(materialBenefits,result);
    }

    private JsonResult<Benefit> getBenefitsJsonResult(List<Goods> goodsList, FullReductionAggregate fullReductionAggregate) {
        FullReductionRule fullReductionRule = fullReductionAggregate.getFullReductionRule();
        if(CollectionUtil.isEmpty(fullReductionRule.getFullReductionStairRules())){
            return JsonResult.create(PromotionResultEnum.NOT_EXIST);
        }
        //适用商品校验
        List<Goods> suitRuleGoodsList =new ArrayList<>();
        for (Goods goods : goodsList) {
            if(SuitRuleVerify.suitRuleGoodsVerify(fullReductionAggregate.getSuitRule(),goods.getSkuId())){
                suitRuleGoodsList.add(goods);
            }else {
                return JsonResult.create();
            }
        }
        // 判断优惠门槛，准备优惠内容
        BigDecimal goodsTotalPrice = new BigDecimal(0);
        int goodsNum = 0;
        if(suitRuleGoodsList.isEmpty()){
            return JsonResult.create();
        }
        for (Goods goods : suitRuleGoodsList) {
            goodsTotalPrice=goodsTotalPrice.add(goods.getPrice().multiply(new BigDecimal(goods.getNum())));
            goodsNum=goodsNum+goods.getNum();
        }
        return fullReductionRule.calcBenefits(goodsTotalPrice, goodsNum);
    }

}
