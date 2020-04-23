package com.afiona.center.client.promotion.util;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * TODO
 *
 * @author LiJinXing
 * @date 2020/4/10
 */
public class MergeUtils {

    public static <T> List<T> mergeList(Collection<List<T>> collections){
        List<T> list =new ArrayList<>();
        collections.forEach(list::addAll);
        return list;
    }

    @SafeVarargs
    public static <K,V>Map<K,Set<V>> mergeMap( Map<K,Set<V>>... maps){
        return Stream.of(maps)
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> {
                    Set<V> mergedSet = Sets.newHashSet();
                    if(CollectionUtil.isNotEmpty(v1)){
                        mergedSet.addAll(v1);
                    }
                    if(CollectionUtil.isNotEmpty(v2)){
                        mergedSet.addAll(v2);
                    }
                    return mergedSet;
                }));
    }

}
