package com.afiona.center.web.promotion.domain.service.benefit;

import com.afiona.center.client.promotion.constants.PromotionType;
import com.afiona.center.client.promotion.domain.aggregate.PayGiftAggregate;
import com.afiona.center.client.promotion.model.Goods;
import com.afiona.center.client.promotion.model.PayGiftBenefit;
import com.afiona.center.client.promotion.model.SuitRuleGoods;
import com.afiona.center.client.promotion.model.query.BenefitCalcQuery;
import com.afiona.center.client.promotion.model.query.PayGiftBenefitCalcQuery;
import com.afiona.center.client.promotion.model.query.PromotionQuery;
import com.afiona.center.web.promotion.domain.repo.PayGiftRepository;
import com.afiona.center.web.promotion.domain.service.benefit.impl.PayGiftApiBenefitCalc;
import com.afiona.center.web.promotion.domain.service.benefit.verify.SuitRuleVerify;
import com.afiona.center.web.promotion.domain.service.promotion.PromotionDetailsService;
import com.afiona.common.util.CloneUtil;
import com.google.common.collect.Lists;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 支付有礼福利
 *
 * @author LiJinXing
 * @date 2020/3/20
 */
@Service
public class PayGiftBenefitService {

    @Resource
    private PayGiftApiBenefitCalc payGiftApiBenefitCalc;

    @Resource
    private PromotionDetailsService promotionDetailsService;

    @Resource
    private PayGiftRepository payGiftRepository;

    public List<PayGiftBenefit> benefit(PayGiftBenefitCalcQuery query) {
        //skuId列表
        List<Long> skuIds = query.getGoodsList().stream().map(Goods::getSkuId).collect(Collectors.toList());
        //获取适合 商品的支付有礼的 活动id
        Set<Long> promotionIds = promotionDetailsService.listSuitGoodsBySkuIds(new HashSet<>(skuIds));
        //查询符合条件的支付有礼活动信息
        List<PayGiftAggregate> payGiftAggregates = payGiftRepository.list(new PromotionQuery().setPromotionTypes(Lists.newArrayList(PromotionType.PAY_GIFT)).setPromotionIds(new ArrayList<>(promotionIds))).getContent();
        //福利赛选集合
        List<PayGiftBenefit> payGiftBenefitList =new ArrayList<>();
        //获取每个支付有礼活动的福利
        for (PayGiftAggregate payGiftAggregate : payGiftAggregates) {
            //适用规则校验
            //适用渠道
            if(!SuitRuleVerify.suitChannelVerify(payGiftAggregate.getSuitRule(),query.getChannelId())){
                continue;
            }
            //适用会员
            if(!SuitRuleVerify.suitMemberVerify(payGiftAggregate.getSuitRule(),query.getMemberId())){
                continue;
            }
            //适用商品
            List<SuitRuleGoods> suitRuleGoodsList =new ArrayList<>();
            List<Goods> goodsList = query.getGoodsList();
            for (Goods goods : goodsList) {
                if(SuitRuleVerify.suitRuleGoodsVerify(payGiftAggregate.getSuitRule(),goods.getSkuId())){
                    suitRuleGoodsList.add(CloneUtil.clone(goods,SuitRuleGoods.class));
                }
            }
            //计算福利
            PayGiftBenefit payGiftBenefit = payGiftApiBenefitCalc.getBenefitsJsonResult(CloneUtil.cloneList(suitRuleGoodsList, BenefitCalcQuery.RelatedGoods.class), payGiftAggregate).getData();
            if(payGiftBenefit!=null){
                payGiftBenefitList.add(payGiftBenefit);
            }
        }
        return payGiftBenefitList;
    }
}
