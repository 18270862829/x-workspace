package com.afiona.center.web.promotion.util;

import com.afiona.center.client.promotion.domain.model.Promotion;
import com.afiona.center.web.promotion.infrastructure.dao.PromotionDAO;
import com.afiona.center.web.promotion.infrastructure.model.PromotionDO;
import com.afiona.common.util.CloneUtil;
import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 活动工具类
 *
 * @author LiJinXing
 * @date 2020/4/1
 */
public class PromotionUtils {

    public static Map<Long, Promotion> mapBySkuIds(Collection<PromotionDO> promotions){
        List<Promotion> simplePromotionList = CloneUtil.cloneList(Lists.newArrayList(promotions), Promotion.class);
        return simplePromotionList.stream().collect(Collectors.toMap(Promotion::getId, Function.identity()));
    }

    public static <T extends PromotionDO>Boolean timeVerify(T t){
        Date startTime = t.getStartTime();
        Date endTime = t.getEndTime();
        Date now = new Date();
        if(now.after(startTime)&&now.before(endTime)){
            return true;
        }
        return false;
    }
}
