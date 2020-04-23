package com.afiona.center.client.promotion.util;


import com.afiona.center.client.promotion.domain.model.Promotion;

import java.util.Date;

/**
 * 促销活动工具类
 *
 * @author LiJinXing
 * @date 2020/4/8
 */
public class PromotionVerifyUtils {

    public static <T extends Promotion>Boolean enabledVerify(T t){
       return t.getEnabled();
    }

    public static <T extends Promotion>Boolean timeVerify(T t){
        Date startTime = t.getStartTime();
        Date endTime = t.getEndTime();
        Date now = new Date();
        if(now.after(startTime)&&now.before(endTime)){
            return true;
        }
        return false;
    }
}
