package com.afiona.center.client.promotion.util;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.afiona.center.client.promotion.domain.model.Promotion;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Maps;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * 泛型辅助类
 *
 * @author dengweiyi
 * @date 2020-02-10
 */
public class GenericHelper {
    public static <T extends IPromotion> Map<Long, List<T>> getDOMap(List<? extends Promotion> promotions, IService<T> dao){
        List<T> DOList = getDOList(promotions, dao);
        Map<Long, List<T>> map = DOList.stream().collect(groupingBy(T::getPromotionId, toList()));
        return map;
    }

    public static <T extends IPromotion> List<T> getDOList(List<? extends Promotion> promotions, IService<T> dao){
        List<Long> promotionIds = promotions.stream().map(Promotion::getId).collect(toList());
        List<T> DOList = dao.list(new QueryWrapper<T>()
                .in("promotion_id", promotionIds));
        return DOList;
    }

    public static <T> boolean hasIdInList(List<T> list, Function<? super T, Long> mapper, long id){
        if(CollectionUtil.isEmpty(list)){
            return true;
        }
        Set<Long> idSet = list.stream().map(mapper).collect(Collectors.toSet());
        return idSet.contains(id);
    }

    public static <T, K, V> Map<K, Set<V>> toMap(List<T> list, Function<T, K> keyFunction, Function<T, V> valueFunction){
        Map<K, List<T>> goodsGroupMap = list.stream()
                .collect(groupingBy(keyFunction));
        Map<K, Set<V>> map = Maps.newHashMap();
        for(Map.Entry<K, List<T>> entry : goodsGroupMap.entrySet()){
            K key = entry.getKey();
            List<T> subList = entry.getValue();
            if(CollectionUtil.isEmpty(subList)){
                continue;
            }
            Set<V> value = list.stream().map(valueFunction).collect(Collectors.toSet());
            map.put(key, value);
        }
        return map;
    }
}
