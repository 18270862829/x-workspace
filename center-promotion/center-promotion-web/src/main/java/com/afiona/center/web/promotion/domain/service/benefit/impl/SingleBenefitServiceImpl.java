package com.afiona.center.web.promotion.domain.service.benefit.impl;

import com.afiona.center.client.promotion.constants.PromotionType;
import com.afiona.center.client.promotion.domain.aggregate.PromotionAggregate;
import com.afiona.center.client.promotion.domain.model.Promotion;
import com.afiona.center.client.promotion.model.Goods;
import com.afiona.center.client.promotion.model.PromotionGroup;
import com.afiona.center.client.promotion.model.SingleBenefit;
import com.afiona.center.client.promotion.model.SingleCommoditiesBenefit;
import com.afiona.center.web.promotion.domain.service.aggregate.IBaseAggregate;
import com.afiona.center.web.promotion.domain.service.aggregate.impl.BaseAggregateService;
import com.afiona.center.web.promotion.domain.service.benefit.SingleBenefitCalcService;
import com.afiona.center.web.promotion.domain.service.benefit.SingleBenefitService;
import com.afiona.center.web.promotion.domain.service.benefit.verify.SuitRuleVerify;
import com.afiona.common.pojo.JsonResult;
import com.afiona.common.util.CloneUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 单个商品福利计算service实现
 *
 * @author LiJinXing
 * @date 2020/3/27
 */
@Service
public class SingleBenefitServiceImpl implements SingleBenefitService {

    @Resource
    private SpecialDiscountBenefitCalcServiceImpl specialDiscountBenefitCalc;

    @Resource
    private IBaseAggregate<PromotionAggregate> iBaseAggregate;

    private static EnumMap<PromotionType, SingleBenefitCalcService> benefitCalcMap;

    @PostConstruct
    public void init(){
        benefitCalcMap = Maps.newEnumMap(PromotionType.class);
        benefitCalcMap.put(PromotionType.SPECIAL_DISCOUNT, specialDiscountBenefitCalc);
    }

    @Override
    public JsonResult<List<SingleCommoditiesBenefit>> benefitCalc(List<PromotionGroup> promotionGroups, List<Goods> goodsList, Long channelId, Long memberId) {
        List<SingleCommoditiesBenefit> singleCommoditiesBenefitList =new ArrayList<>();
        Map<Long, PromotionGroup> groupMap = promotionGroups.stream().collect(Collectors.toMap(PromotionGroup::getSkuId, Function.identity()));
        for (Goods goods : goodsList) {
            PromotionGroup promotionGroup = groupMap.get(goods.getSkuId());
            if(promotionGroup==null){
                singleCommoditiesBenefitList.add(noBenefit(goods));
                continue;
            }
            List<Promotion> promotions = promotionGroup.getPromotions().stream().filter(Objects::nonNull).collect(Collectors.toList());
            //分别计算每个活动
            SingleCommoditiesBenefit singleCommoditiesBenefit = new SingleCommoditiesBenefit().setSkuId(goods.getSkuId());
            List<SingleBenefit> singleBenefitList = new ArrayList<>();
            for (Promotion promotion : promotions) {
                    singleBenefitList.add(singlePromotionBenefit(goods,promotion,channelId,memberId));
            }
            //排序
            Collections.sort(singleBenefitList);
            singleCommoditiesBenefit.setSingleBenefitList(singleBenefitList);
            singleCommoditiesBenefitList.add(singleCommoditiesBenefit);
        }
        return JsonResult.create(singleCommoditiesBenefitList);
    }

    @Override
    public SingleBenefit singlePromotionBenefit(Goods goods,Promotion promotion,Long channelId, Long memberId){
        SingleBenefitCalcService singleBenefitCalcService = benefitCalcMap.get(promotion.getType());
        if(singleBenefitCalcService==null){
            return noSingleBenefit(goods);
        }
        PromotionAggregate promotionAggregate = CloneUtil.clone(promotion, PromotionAggregate.class);
        iBaseAggregate.aggregate(Lists.newArrayList(promotionAggregate));
        //适用渠道校验
        if(!SuitRuleVerify.suitChannelVerify(promotionAggregate.getSuitRule(),channelId)){
            return noSingleBenefit(goods);
        }
        //适用会员校验
        if(!SuitRuleVerify.suitMemberVerify(promotionAggregate.getSuitRule(),memberId)){
            return noSingleBenefit(goods);
        }
        if(!SuitRuleVerify.suitRuleGoodsVerify(promotionAggregate.getSuitRule(),goods.getSkuId())){
            return noSingleBenefit(goods);
        }
        return singleBenefitCalcService.calcBenefits(promotion, goods);
    }

    private SingleCommoditiesBenefit noBenefit(Goods goods) {
        SingleCommoditiesBenefit singleCommoditiesBenefit = new SingleCommoditiesBenefit();
        singleCommoditiesBenefit.setSkuId(goods.getSkuId());
        singleCommoditiesBenefit.setSingleBenefitList(Lists.newArrayList(noSingleBenefit(goods)));
        return singleCommoditiesBenefit;
    }

    private SingleBenefit noSingleBenefit(Goods goods){
        return new SingleBenefit().setSkuId(goods.getSkuId()).setPreferentialAmount(new BigDecimal(0)).setPrice(goods.getPrice());
    }


}
