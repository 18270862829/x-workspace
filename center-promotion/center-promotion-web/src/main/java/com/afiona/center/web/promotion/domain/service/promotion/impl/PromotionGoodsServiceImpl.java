package com.afiona.center.web.promotion.domain.service.promotion.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.afiona.center.client.promotion.constants.PromotionType;
import com.afiona.center.client.promotion.constants.choice.SuitChoiceEnum;
import com.afiona.center.client.promotion.domain.aggregate.PromotionAggregate;
import com.afiona.center.client.promotion.domain.model.suitrule.SuitGoods;
import com.afiona.center.client.promotion.domain.model.suitrule.SuitRule;
import com.afiona.center.client.promotion.model.PromotionSuitGoods;
import com.afiona.center.web.promotion.domain.service.promotion.PromotionGoodsService;
import com.afiona.center.web.promotion.infrastructure.dao.FixedPriceGoodsDAO;
import com.afiona.center.web.promotion.infrastructure.model.FixedPriceGoodsDO;
import com.afiona.common.pojo.JsonResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 促销商品service实现
 *
 * @author LiJinXing
 * @date 2020/4/15
 */
@Service
public class PromotionGoodsServiceImpl implements PromotionGoodsService {

    @Resource
    private FixedPriceGoodsDAO fixedPriceGoodsDAO;

    @Override
    public JsonResult<PromotionSuitGoods> suitGoods(PromotionAggregate promotionAggregate) {
        //一口价没有适用商品，单独考虑
        if(promotionAggregate.getType().equals(PromotionType.FIXED_PRICE)){
            return fixedPriceGoods(promotionAggregate);
        }
        SuitRule suitRule = promotionAggregate.getSuitRule();
        SuitChoiceEnum suitGoodsChoice =suitRule.getSuitGoodsChoice();
        if(suitGoodsChoice==null){
            return null;
        }
        List<SuitGoods> suitGoods = suitRule.getSuitGoods();
        if(CollectionUtil.isEmpty(suitGoods)){
            return null;
        }
        Set<Long> set = suitGoods.stream().map(SuitGoods::getSkuId).filter(Objects::nonNull).collect(Collectors.toSet());
        PromotionSuitGoods promotionSuitGoods = new PromotionSuitGoods().setSuitGoodsChoice(suitGoodsChoice).setSkuIds(Lists.newArrayList(set));
        return JsonResult.create(promotionSuitGoods);
    }

    private JsonResult<PromotionSuitGoods> fixedPriceGoods(PromotionAggregate promotionAggregate) {
        List<FixedPriceGoodsDO> list = fixedPriceGoodsDAO.list(new LambdaQueryWrapper<FixedPriceGoodsDO>().eq(FixedPriceGoodsDO::getPromotionId, promotionAggregate.getId()));
        if(CollectionUtil.isEmpty(list)){
            return null;
        }
        Set<Long> set = list.stream().map(FixedPriceGoodsDO::getSkuId).collect(Collectors.toSet());
        return JsonResult.create(new PromotionSuitGoods().setSkuIds(Lists.newArrayList(set)).setSuitGoodsChoice(SuitChoiceEnum.SUIT));
    }
}
