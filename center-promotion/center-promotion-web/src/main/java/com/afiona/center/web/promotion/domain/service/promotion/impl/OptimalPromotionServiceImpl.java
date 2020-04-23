package com.afiona.center.web.promotion.domain.service.promotion.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.afiona.center.client.promotion.constants.PromotionType;
import com.afiona.center.client.promotion.constants.StatusType;
import com.afiona.center.client.promotion.domain.model.Promotion;
import com.afiona.center.client.promotion.model.query.PromotionQuery;
import com.afiona.center.client.promotion.util.MergeUtils;
import com.afiona.center.web.promotion.domain.service.promotion.OptimalPromotionService;
import com.afiona.center.web.promotion.domain.service.promotion.PromotionDetailsService;
import com.afiona.center.web.promotion.infrastructure.dao.PromotionDAO;
import com.afiona.center.web.promotion.infrastructure.model.PromotionDO;
import com.afiona.common.util.CloneUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 最优促销活动service实现
 *
 * @author LiJinXing
 * @date 2020/3/26
 */
@Service
public class OptimalPromotionServiceImpl implements OptimalPromotionService {

    private static List<PromotionType> promotionTypeList =new ArrayList<>();

    static {
        promotionTypeList.add(PromotionType.FIXED_PRICE);
        promotionTypeList.add(PromotionType.FULL_REDUCTION);
        promotionTypeList.add(PromotionType.ADD_MONEY_TO_BUY_GOODS);
    }

    @Resource
    private PromotionDetailsService promotionDetailsService;

    @Override
    public Promotion optimalPromotion(Long skuId) {
        List<Promotion> promotions = promotionListBySkuId(skuId);
        return CollectionUtil.isEmpty(promotions)?null:promotions.get(0);
    }

    @Override
    public Map<Long, Promotion> optimalPromotion(List<Long> skuIds) {
        Map<Long,Promotion> map =new HashMap<>(16);
        Map<Long, List<Promotion>> listMap = promotionListBySkuIds(skuIds);
        if(CollectionUtil.isNotEmpty(listMap)){
            Set<Map.Entry<Long, List<Promotion>>> entrySet = listMap.entrySet();
            for (Map.Entry<Long, List<Promotion>> entry : entrySet) {
                map.put(entry.getKey(),entry.getValue().get(0));
            }
        }
        return map;
    }

    @Override
    public List<Promotion> promotionListBySkuId(Long skuId) {

        Map<Long, List<Promotion>> map = promotionDetailsService.listGroupBySkuIdsMap(Sets.newHashSet(skuId), promotionTypeList);
        List<Promotion> promotionList = map.get(skuId);
        if(CollectionUtil.isEmpty(promotionList)){
            return null;
        }
        //排序
        Collections.sort(promotionList);
        return promotionList;
    }

    @Override
    public Map<Long, List<Promotion>> promotionListBySkuIds(List<Long> skuIds) {
        return promotionDetailsService.listGroupBySkuIdsMap(Sets.newHashSet(skuIds), promotionTypeList);

    }
}
