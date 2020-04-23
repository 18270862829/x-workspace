package com.afiona.center.web.promotion.domain.service.verify.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.afiona.center.client.promotion.constants.StatusType;
import com.afiona.center.client.promotion.domain.model.IPromotion;
import com.afiona.center.client.promotion.domain.model.Promotion;
import com.afiona.center.web.promotion.domain.service.verify.ActivityVerify;
import com.afiona.center.web.promotion.infrastructure.dao.ActivityDAO;
import com.afiona.center.web.promotion.infrastructure.model.ActivityDO;
import com.afiona.center.web.promotion.infrastructure.model.PromotionDO;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 活动校验实现
 *
 * @author LiJinXing
 * @date 2020/4/15
 */
@Service
public class ActivityVerifyImpl implements ActivityVerify {

    @Resource
    private ActivityDAO activityDAO;

    @Override
    public <T extends IPromotion>List<T> effectiveFilter(Collection<T> promotions) {
        List<Long> activityIds = promotions.stream().map(T::getActivityId).collect(Collectors.toList());
        Collection<ActivityDO> dos = activityDAO.listByIds(activityIds);
        if(CollectionUtil.isEmpty(dos)){
            promotions.clear();
            return null;
        }
        List<Long> effectiveActivityId = dos.stream().filter(ActivityDO::getEnabled).filter(activityDO -> activityDO.getStatus().equals(StatusType.PROCESSING)).map(ActivityDO::getId).collect(Collectors.toList());
        return promotions.stream().filter(promotion->effectiveActivityId.contains(promotion.getActivityId())).collect(Collectors.toList());
    }
}
