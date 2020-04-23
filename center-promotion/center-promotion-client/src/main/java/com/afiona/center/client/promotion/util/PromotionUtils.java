package com.afiona.center.client.promotion.util;

import cn.hutool.core.util.NumberUtil;
import com.afiona.center.client.promotion.domain.model.IPromotion;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 活动工具类
 *
 * @author LiJinXing
 * @date 2020/4/1
 */
public class PromotionUtils {

    public static <T extends IPromotion>Map<Long, T> mapBySkuIds(Collection<T> promotions){
        return promotions.stream().collect(Collectors.toMap(T::getId, Function.identity()));
    }

    public static <T extends IPromotion>Boolean timeVerify(T t){
        Date startTime = t.getStartTime();
        Date endTime = t.getEndTime();
        Date now = new Date();
        if(now.after(startTime)&&now.before(endTime)){
            return true;
        }
        return false;
    }


    public static <T extends IPromotion>int compareTo(T thisT, T oT){
        Long thisPriority = thisT.getPriority();
        Long oPriority = oT.getPriority();
        if(thisPriority!=null&&oPriority!=null){
            return -NumberUtil.compare(thisPriority,oPriority);
        }else {
            return oPriority!=null?1:-1;
        }
    }

}
