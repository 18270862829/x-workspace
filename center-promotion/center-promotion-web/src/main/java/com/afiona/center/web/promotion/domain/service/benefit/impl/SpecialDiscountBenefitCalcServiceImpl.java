package com.afiona.center.web.promotion.domain.service.benefit.impl;

import com.afiona.center.client.promotion.constants.choice.DiscountChoiceEnum;
import com.afiona.center.client.promotion.constants.choice.GlobalDiscountSettingChoice;
import com.afiona.center.client.promotion.constants.choice.IgnoreSmallChangeChoiceEnum;
import com.afiona.center.client.promotion.domain.aggregate.SpecialDiscountAggregate;
import com.afiona.center.client.promotion.domain.model.Promotion;
import com.afiona.center.client.promotion.domain.model.promotionrule.specialdiscount.SpecialDiscountGoods;
import com.afiona.center.client.promotion.domain.model.promotionrule.specialdiscount.SpecialDiscountRule;
import com.afiona.center.client.promotion.model.Goods;
import com.afiona.center.client.promotion.model.SingleBenefit;
import com.afiona.center.web.promotion.domain.service.aggregate.impl.SpecialDiscountAggregateService;
import com.afiona.center.web.promotion.domain.service.benefit.SingleBenefitCalcService;
import com.afiona.common.util.CloneUtil;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 限时折扣促销活动福利计算
 *
 * @author dengweiyi
 * @date 2020-02-11
 */
@Component
public class SpecialDiscountBenefitCalcServiceImpl implements SingleBenefitCalcService {

    @Resource
    private SpecialDiscountAggregateService specialDiscountAggregateService;

    @Override
    public SingleBenefit calcBenefits(Promotion promotion, Goods goods) {

        SpecialDiscountAggregate specialDiscountAggregate = CloneUtil.clone(promotion, SpecialDiscountAggregate.class);
        specialDiscountAggregateService.aggregate(Lists.newArrayList(specialDiscountAggregate));
        return getBenefits(goods, specialDiscountAggregate);
    }

    private SingleBenefit getBenefits(Goods goods, SpecialDiscountAggregate specialDiscountAggregate) {
        SpecialDiscountRule specialDiscountRule = specialDiscountAggregate.getSpecialDiscountRule();
        if(specialDiscountRule==null){
            return null;
        }
        //获取符合优惠条件的skuId
        Map<Long, SpecialDiscountGoods> goodsMap = specialDiscountAggregate.getSpecialDiscountRule().getSpecialDiscountGoods().stream().collect(Collectors.toMap(SpecialDiscountGoods::getSkuId, Function.identity()));
        Set<Long> skuIds = goodsMap.keySet();
        //商品原价
        BigDecimal price = goods.getPrice();
        //抹零操作(先抹零后打折或减价)
        IgnoreSmallChangeChoiceEnum ignoreSmallChangeChoice = specialDiscountRule.getIgnoreSmallChangeChoice();
        //抹去分+角
        if(ignoreSmallChangeChoice.equals(IgnoreSmallChangeChoiceEnum.FEN_AND_JIAO)){
            if(skuIds.contains(goods.getSkuId())){
                goods.setPrice(price.setScale(0,BigDecimal.ROUND_DOWN));
            }
            //抹去分
        } else if(ignoreSmallChangeChoice.equals(IgnoreSmallChangeChoiceEnum.ONLY_FEN)){
            if(skuIds.contains(goods.getSkuId())){
                goods.setPrice(goods.getPrice().setScale(1,BigDecimal.ROUND_DOWN));
            }
        }
        //获取全局设置
        GlobalDiscountSettingChoice choice = specialDiscountRule.getGlobalDiscountSetting().getChoice();
        //记录是否全局设置
        boolean flg=false;
        if(choice.equals(GlobalDiscountSettingChoice.AMOUNT)){
            //全局减x元
            BigDecimal amount = specialDiscountRule.getGlobalDiscountSetting().getAmount();
            //为空或者减0元表示无全局设置
            if(amount!=null&&!amount.equals(new BigDecimal(0))){
                flg=true;
                //符合条件的商品全部减x元
                 if(skuIds.contains(goods.getSkuId())){
                     goods.setPrice(goods.getPrice().subtract(amount));
                 }
            }
        }
        if(choice.equals(GlobalDiscountSettingChoice.DISCOUNT)){
            //全局打x折
            BigDecimal discount = specialDiscountRule.getGlobalDiscountSetting().getDiscount();
            //为空或打0折表示无全局设置
            if(discount!=null&&!discount.equals(new BigDecimal(0))){
                flg=true;
                if(skuIds.contains(goods.getSkuId())){
                    goods.setPrice(goods.getPrice().multiply(discount));
                }
            }
        }
        if(!flg){
            //没有全局设置的情况，单独计算每个sku的优惠
            //获取对应的关联商品规则
            SpecialDiscountGoods specialDiscountGoods = goodsMap.get(goods.getSkuId());
            if(specialDiscountGoods==null){
                //未找到对应关联商品规则，则不考虑优惠
              return null;
            }
            //获取优惠类型打折或减元
            DiscountChoiceEnum discountChoice = specialDiscountGoods.getDiscountChoice();
            if(discountChoice.equals(DiscountChoiceEnum.AMOUNT)){
                //减x元
                goods.setPrice(goods.getPrice().subtract(specialDiscountGoods.getAmount()));
            }
            if(discountChoice.equals(DiscountChoiceEnum.DISCOUNT)){
                //打x折
                goods.setPrice(goods.getPrice().multiply(specialDiscountGoods.getDiscount()));
            }
        }

        //计算优惠后的金额
        return new SingleBenefit().setPromotion(specialDiscountAggregate).setSkuId(goods.getSkuId()).setPrice(price).setPreferentialAmount(price.subtract(goods.getPrice()));
    }


}
