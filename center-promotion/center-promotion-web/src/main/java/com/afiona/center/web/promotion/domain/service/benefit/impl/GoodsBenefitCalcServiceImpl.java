package com.afiona.center.web.promotion.domain.service.benefit.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.afiona.center.client.promotion.constants.PromotionResultEnum;
import com.afiona.center.client.promotion.constants.PromotionType;
import com.afiona.center.client.promotion.model.Goods;
import com.afiona.center.client.promotion.model.PromotionGroup;
import com.afiona.center.client.promotion.model.SingleCommoditiesBenefit;
import com.afiona.center.client.promotion.model.query.GoodsBenefitCalcQuery;
import com.afiona.center.web.promotion.domain.service.benefit.GoodsBenefitCalcService;
import com.afiona.center.web.promotion.domain.service.benefit.SingleBenefitService;
import com.afiona.center.web.promotion.domain.service.promotion.PromotionDetailsService;
import com.afiona.common.pojo.JsonResult;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 商品福利计算
 * @author huangxiangfu
 * @date 2020/3/19
 */
@Service
public class GoodsBenefitCalcServiceImpl implements GoodsBenefitCalcService {

    @Resource
    private PromotionDetailsService promotionDetailsService;

    @Resource
    private SingleBenefitService singleBenefitService;

    private static List<PromotionType> singlePromotionTypes =new ArrayList<>();

    static {
        singlePromotionTypes.add(PromotionType.SPECIAL_DISCOUNT);
    }

    @Override
    public  JsonResult<List<SingleCommoditiesBenefit>> benefitCalc(GoodsBenefitCalcQuery query) {
        //参数校验
        List<Goods> goodsList = query.getGoodsList();
        if(CollectionUtils.isEmpty(goodsList)) {
            return JsonResult.create(PromotionResultEnum.EMPTY);
        }
        //获取所有参与活动得skuId
        List<Long> skuIdList = goodsList.stream().map(Goods::getSkuId).collect(Collectors.toList());
        if(CollectionUtil.isEmpty(skuIdList)) {
            return JsonResult.create(PromotionResultEnum.EMPTY);
        }
        List<PromotionGroup> promotionGroups = promotionDetailsService.listGroupBySkuIds(new HashSet<>(skuIdList),singlePromotionTypes);
        if(CollectionUtil.isEmpty(promotionGroups)){
            return null;
        }
        return singleBenefitService.benefitCalc(promotionGroups,goodsList,query.getChannelId(),query.getMemberId());

    }


}
