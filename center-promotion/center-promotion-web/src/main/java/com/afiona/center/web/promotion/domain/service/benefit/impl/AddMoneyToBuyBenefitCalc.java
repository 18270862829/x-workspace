package com.afiona.center.web.promotion.domain.service.benefit.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.afiona.center.client.promotion.constants.PromotionResultEnum;
import com.afiona.center.client.promotion.constants.choice.DiscountsNumberEnum;
import com.afiona.center.client.promotion.constants.choice.ThresholdChoiceEnum;
import com.afiona.center.client.promotion.domain.aggregate.AddMoneyToBuyAggregate;
import com.afiona.center.client.promotion.domain.model.Promotion;
import com.afiona.center.client.promotion.domain.model.promotionrule.addmoneytobuy.AddMoneyToBuyRule;
import com.afiona.center.client.promotion.model.*;
import com.afiona.center.web.promotion.domain.service.aggregate.impl.AddMoneyToBuyAggregateService;
import com.afiona.center.web.promotion.domain.service.benefit.MultipleBenefitCalc;
import com.afiona.center.web.promotion.domain.service.benefit.verify.SuitRuleVerify;
import com.afiona.common.pojo.JsonResult;
import com.afiona.common.util.CloneUtil;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * 加价购活动福利计算
 *
 * @author LiJinXing
 * @date 2020/2/24
 */
@Component
public class AddMoneyToBuyBenefitCalc implements MultipleBenefitCalc {

    @Resource
    private AddMoneyToBuyAggregateService addMoneyToBuyAggregateService;

    @Override
    public JsonResult calcBenefits(Promotion promotion, List<Goods> goodsList, MaterialBenefits materialBenefits) {
        AddMoneyToBuyAggregate addMoneyToBuyAggregate = CloneUtil.clone(promotion, AddMoneyToBuyAggregate.class);
        addMoneyToBuyAggregateService.aggregate(Lists.newArrayList(addMoneyToBuyAggregate));
        JsonResult<Benefit> result = getBenefitsJsonResult(goodsList, addMoneyToBuyAggregate);
        if(result.getData()!=null){
            materialBenefits.getBenefitList().add(result.getData());
        }
        return result;
    }

    private JsonResult<Benefit> getBenefitsJsonResult(List<Goods> goodsList, AddMoneyToBuyAggregate addMoneyToBuyAggregate) {
        AddMoneyToBuyRule addMoneyToBuyRule = addMoneyToBuyAggregate.getAddMoneyToBuyRule();
        if(CollectionUtil.isEmpty(addMoneyToBuyRule.getAddMoneyToBuyGoodsList())){
            return JsonResult.create(PromotionResultEnum.DATA_ERROR);
        }
        //适用商品校验，返回适用的商品信息
        List<Goods> suitRuleGoodsList =new ArrayList<>();
        for (Goods goods : goodsList) {
            if(SuitRuleVerify.suitRuleGoodsVerify(addMoneyToBuyAggregate.getSuitRule(),goods.getSkuId())){
                suitRuleGoodsList.add(goods);
            }
        }
        if(suitRuleGoodsList.isEmpty()){
            return JsonResult.create(PromotionResultEnum.GOODS_UNQUALIFIED);
        }
       //列表中包含的适用商品总价
        BigDecimal suitTotalPrice= new BigDecimal(0);
        int totalNum =0;
        for (Goods goods : suitRuleGoodsList) {
            suitTotalPrice=suitTotalPrice.add(goods.getPrice().multiply(new BigDecimal(goods.getNum())));
            totalNum=totalNum+goods.getNum();
        }
        //计算活动门槛 此处只考虑非换购商品达到活动门槛，不考虑换购商品原价金额达到门槛和循环换购问题
        Benefit benefit=new Benefit();
        //判断门槛
        boolean flg = ((addMoneyToBuyRule.getThresholdChoice().equals(ThresholdChoiceEnum.AMOUNT)) && (addMoneyToBuyRule.getFullAmount().compareTo(suitTotalPrice) < 0)) || ((addMoneyToBuyRule.getThresholdChoice().equals(ThresholdChoiceEnum.NUM)) && (addMoneyToBuyRule.getFullNum().compareTo(totalNum) < 0));
        if(flg){
            List<AddBuyGoods> addBuyGoods = CloneUtil.cloneList(addMoneyToBuyRule.getAddMoneyToBuyGoodsList(), AddBuyGoods.class);
            benefit.setAddBuyGoodsList(addBuyGoods);
            //达到门槛 判断换购数量
            if(addMoneyToBuyRule.getDiscountsNumber().equals(DiscountsNumberEnum.TO_ONE)){
                //换购一件
                benefit.setDiscountsNumberEnum(DiscountsNumberEnum.TO_ONE);
            }
            else if(addMoneyToBuyRule.getDiscountsNumber().equals(DiscountsNumberEnum.T0_MANY)){
                //可换购多件
                benefit.setDiscountsNumberEnum(DiscountsNumberEnum.T0_MANY);
            }
        }
        return JsonResult.create(benefit);
    }
}
