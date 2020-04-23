package com.afiona.center.web.promotion.domain.service.promotion.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.afiona.center.client.promotion.constants.PromotionType;
import com.afiona.center.client.promotion.domain.model.Promotion;
import com.afiona.center.client.promotion.model.*;
import com.afiona.center.client.promotion.model.query.BenefitCalcQuery;
import com.afiona.center.web.promotion.domain.service.benefit.MultipleBenefitCalc;
import com.afiona.center.web.promotion.domain.service.benefit.SingleBenefitService;
import com.afiona.center.web.promotion.domain.service.benefit.impl.AddMoneyToBuyBenefitCalc;
import com.afiona.center.web.promotion.domain.service.benefit.impl.FixedPriceBenefitCalc;
import com.afiona.center.web.promotion.domain.service.benefit.impl.FullReductionBenefitCalc;
import com.afiona.center.web.promotion.domain.service.promotion.PromotionBenefitsCalcService;
import com.afiona.center.web.promotion.infrastructure.dao.PromotionDAO;
import com.afiona.center.web.promotion.infrastructure.model.PromotionDO;
import com.afiona.center.client.promotion.util.PromotionUtils;
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
 * 促销福利计算service实现
 *
 * @author LiJinXing
 * @date 2020/3/26
 */
@Service
public class PromotionBenefitsCalcServiceImpl implements PromotionBenefitsCalcService {

    @Resource
    private SingleBenefitService singleBenefitService;

    @Resource
    private PromotionDAO promotionDAO;

    @Resource
    private FullReductionBenefitCalc fullReductionBenefitCalc;

    @Resource
    private FixedPriceBenefitCalc fixedPriceBenefitCalc;

    @Resource
    private AddMoneyToBuyBenefitCalc addMoneyToBuyBenefitCalc;

    private static EnumMap<PromotionType, MultipleBenefitCalc> benefitCalcMap;

    @PostConstruct
    public void init(){
        benefitCalcMap = Maps.newEnumMap(PromotionType.class);
        benefitCalcMap.put(PromotionType.FIXED_PRICE,fixedPriceBenefitCalc);
        benefitCalcMap.put(PromotionType.FULL_REDUCTION, fullReductionBenefitCalc);
        benefitCalcMap.put(PromotionType.ADD_MONEY_TO_BUY_GOODS,addMoneyToBuyBenefitCalc);
    }

    @Override
    public JsonResult<MaterialBenefits> benefitsCalc(BenefitCalcQuery query) {

        List<BenefitCalcQuery.InvolvedPromotion> involvedPromotions = query.getInvolvedPromotions();
        if(involvedPromotions==null||involvedPromotions.isEmpty()){
            return JsonResult.create(5000,"BenefitCalcQuery.InvolvedPromotion 无数据");
        }
        Set<Long> promotionIds = involvedPromotions.stream().map(BenefitCalcQuery.InvolvedPromotion::getPromotionId).collect(Collectors.toSet());
        Collection<PromotionDO> promotionDOList = promotionDAO.listByIds(promotionIds);
        if(promotionDOList==null||promotionDOList.isEmpty()){
            return JsonResult.create(5000,"数据异常，整体促销活动promotionId不存在");
        }
        List<Promotion> promotionList = CloneUtil.cloneList(Lists.newArrayList(promotionDOList), Promotion.class);
        //排序
        Collections.sort(promotionList);
        Map<Long, Promotion> mapPromotion = promotionList.stream().collect(Collectors.toMap(Promotion::getId, Function.identity()));
        MaterialBenefits materialBenefits =new MaterialBenefits().setBenefitList(new ArrayList<>());
        //查询所有单品促销活动
        Set<BenefitCalcQuery.RelatedGoods> relatedGoodsSet =new HashSet<>();
        involvedPromotions.forEach(involvedPromotion -> relatedGoodsSet.addAll(involvedPromotion.getRelatedGoods()));
        Set<Long> singlePromotionSet = relatedGoodsSet.stream().map(BenefitCalcQuery.RelatedGoods::getSinglePromotionId).collect(Collectors.toSet());
        Collection<PromotionDO> singlePromotions = promotionDAO.listByIds(singlePromotionSet);
        if(CollectionUtil.isEmpty(singlePromotions)){
            return JsonResult.create(5000,"数据异常，整体促销活动promotionId不存在");
        }
        Map<Long, Promotion> simplePromotionMap = PromotionUtils.mapBySkuIds(CloneUtil.cloneList(Lists.newArrayList(singlePromotions),Promotion.class));

        for (BenefitCalcQuery.InvolvedPromotion involvedPromotion : involvedPromotions) {
            List<BenefitCalcQuery.RelatedGoods> relatedGoodsList = involvedPromotion.getRelatedGoods();
            if(relatedGoodsList==null||relatedGoodsList.isEmpty()){
                continue;
            }
            List<SingleBenefit> singleBenefitList =new ArrayList<>();
            List<Goods> goodsList =new ArrayList<>();
            //单品促销
            Set<Long> singlePromotionIds = relatedGoodsList.stream().map(BenefitCalcQuery.RelatedGoods::getSinglePromotionId).collect(Collectors.toSet());
            List<Promotion> promotions =new ArrayList<>();
            for (Long singlePromotionId : singlePromotionIds) {
                Promotion promotion = simplePromotionMap.get(singlePromotionId);
                if(promotion!=null){
                    promotions.add(promotion);
                }
            }
            //排序
            Collections.sort(promotions);
            Map<Long, Promotion> promotionMap = promotions.stream().collect(Collectors.toMap(Promotion::getId, Function.identity()));
            for (BenefitCalcQuery.RelatedGoods relatedGoods : relatedGoodsList) {
                Promotion promotion = promotionMap.get(relatedGoods.getSinglePromotionId());
                if(promotion!=null){
                    SingleBenefit singleBenefit = singleBenefitService.singlePromotionBenefit(CloneUtil.clone(relatedGoods, Goods.class), promotion, query.getChannelId(), query.getMemberId());
                    singleBenefitList.add(singleBenefit);
                    //设置单品优惠后的金额，为总体促销做准备
                    BigDecimal subtract = singleBenefit.getPrice().subtract(singleBenefit.getPreferentialAmount());
                    goodsList.add(new Goods().setSkuId(singleBenefit.getSkuId()).setPrice(subtract).setNum(relatedGoods.getNum()));
                }else {
                    goodsList.add(new Goods().setSkuId(relatedGoods.getSkuId()).setPrice(relatedGoods.getPrice()).setNum(relatedGoods.getNum()));
                }
            }
            //校验活动类型是整体促销
            Promotion promotion = mapPromotion.get(involvedPromotion.getPromotionId());
            if(promotion==null){
                return JsonResult.create(5000,"数据异常,该促销已失效");
            }
            MultipleBenefitCalc multipleBenefitCalc = benefitCalcMap.get(promotion.getType());
            if(multipleBenefitCalc==null){
                return JsonResult.create(5000,"数据异常，promotionId不是整体促销id");
            }
            //计算整体促销活动福利
            JsonResult result = multipleBenefitCalc.calcBenefits(promotion, goodsList, materialBenefits);
            if(result.getCode()!=200){
                return JsonResult.create(result.getCode(),result.getMsg());
            }
            //设置单品促销信息
            materialBenefits.getBenefitList().get(materialBenefits.getBenefitList().size()-1).setSingleBenefitList(singleBenefitList).setPromotion(promotion);
        }
        return JsonResult.create(materialBenefits);
    }
}
