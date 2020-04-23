package com.afiona.center.web.promotion.domain.service.benefit.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.afiona.center.client.promotion.constants.PromotionResultEnum;
import com.afiona.center.client.promotion.constants.choice.LimitChoiceEnum;
import com.afiona.center.client.promotion.domain.aggregate.FixedPriceAggregate;
import com.afiona.center.client.promotion.domain.model.Promotion;
import com.afiona.center.client.promotion.domain.model.promotionrule.fixedprice.FixedPriceGoods;
import com.afiona.center.client.promotion.domain.model.promotionrule.fixedprice.FixedPriceRule;
import com.afiona.center.client.promotion.model.Benefit;
import com.afiona.center.client.promotion.model.Goods;
import com.afiona.center.client.promotion.model.MaterialBenefits;
import com.afiona.center.web.promotion.domain.service.aggregate.impl.FixedPriceAggregateService;
import com.afiona.center.web.promotion.domain.service.benefit.MultipleBenefitCalc;
import com.afiona.center.web.promotion.util.FillUtils;
import com.afiona.common.pojo.JsonResult;
import com.afiona.common.util.CloneUtil;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 一口价促销活动福利计算
 *
 * @author dengweiyi
 * @date 2020-02-11
 */
@Component
public class FixedPriceBenefitCalc implements MultipleBenefitCalc {

    @Resource
    private FixedPriceAggregateService fixedPriceAggregateService;

    @Override
    public JsonResult calcBenefits(Promotion promotion, List<Goods> goodsList, MaterialBenefits materialBenefits) {
        FixedPriceAggregate fixedPriceAggregate = CloneUtil.clone(promotion, FixedPriceAggregate.class);
        fixedPriceAggregateService.aggregate(Lists.newArrayList(fixedPriceAggregate));
        JsonResult<Benefit> result = getBenefitsJsonResult(goodsList, fixedPriceAggregate);
        return FillUtils.fillResult(materialBenefits,result);
    }

    private JsonResult<Benefit> getBenefitsJsonResult(List<Goods> goodsList, FixedPriceAggregate fixedPriceAggregate) {
        FixedPriceRule fixedPriceRule = fixedPriceAggregate.getFixedPriceRule();
        if(CollectionUtil.isEmpty(fixedPriceRule.getFixedPriceGoods())){
            return JsonResult.create(PromotionResultEnum.DATA_ERROR);
        }
        //获取购买的商品中 符合一口价规则的skuId
        Set<Long> skuIds = fixedPriceRule.getFixedPriceGoods().stream().collect(Collectors.toMap(FixedPriceGoods::getSkuId, Function.identity())).keySet();
        List<Long> skuIdList =new ArrayList<>();
        //存储符合规定的skuId
        for (Goods goods : goodsList) {
            if(skuIds.contains(goods.getSkuId())){
                for (int i = 0; i <goods.getNum() ; i++) {
                    skuIdList.add(goods.getSkuId());
                }
            }
        }
        //任选 goodsNum件
        Integer goodsNum = fixedPriceRule.getGoodsNum();
        //商品限制，可重复购，或不可重复购
        if(fixedPriceRule.getLimitChoice().equals(LimitChoiceEnum.REPEAT_NOT_ALLOWED)){
            List<Long> list = new ArrayList<>();
            //一组中第几个商品
            int skuNum =0;
            //第几组商品
           List<Long> group =new ArrayList<>();
            for (Long skuId : skuIdList) {
                if(skuNum<=goodsNum){
                    if(group.contains(skuId)){
                        continue;
                    }
                    skuNum++;
                    group.add(skuId);
                    continue;
                }
                list.addAll(group);
                group.clear();
                skuNum=1;
                group.add(skuId);
            }
            skuIdList.clear();
            skuIdList.addAll(list);
        }
        //计算实际优惠的件数
        int num=skuIdList.size()/goodsNum*goodsNum;
        //享受活动的商品的优惠后的金额
        BigDecimal preferentialPrice=fixedPriceRule.getAmount().multiply(new BigDecimal(num));
        //享受活动的商品的原价总价
        BigDecimal totalPrice=new BigDecimal(0);
        if(goodsList.isEmpty()){
            return JsonResult.create();
        }
        for (Goods goods : goodsList) {
            totalPrice=totalPrice.add(goods.getPrice().multiply(new BigDecimal(goods.getNum())));
        }
        //计算福利
        Benefit benefit = new Benefit().setPreferentialAmount(totalPrice.subtract(preferentialPrice));
        return JsonResult.create(benefit);
    }
}
