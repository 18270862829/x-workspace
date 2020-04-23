package com.afiona.center.web.promotion.domain.service.verify.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.afiona.center.client.promotion.constants.choice.OperatingTypeEnum;
import com.afiona.center.client.promotion.domain.model.IPromotion;
import com.afiona.center.client.promotion.domain.model.Promotion;
import com.afiona.center.client.promotion.util.OperatingVerifyUtils;
import com.afiona.center.client.promotion.util.PromotionUtils;
import com.afiona.center.web.promotion.domain.service.verify.ActivityVerify;
import com.afiona.center.web.promotion.domain.service.verify.PromotionVerify;
import com.afiona.center.web.promotion.infrastructure.dao.PromotionDAO;
import com.afiona.center.web.promotion.infrastructure.model.PromotionDO;
import com.afiona.common.util.BizException;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 促销校验
 *
 * @author LiJinXing
 * @date 2020/4/15
 */
@Service
public class PromotionVerifyImpl implements PromotionVerify {

    @Resource
    private PromotionDAO promotionDAO;

    @Resource
    private ActivityVerify activityVerify;

    @Override
    public void statusVerify(Long promotionId,OperatingTypeEnum operatingType) {
        PromotionDO promotionDO = promotionDAO.getById(promotionId);
        if(promotionDO==null){
            return;
        }
        if(!OperatingVerifyUtils.enabledSetting(promotionDO.getEnabled(),promotionDO.getStatus(), operatingType)){
            throw new BizException(operatingType.getMessage()+"失败,该促销可能是已启用状态且未开始或进行中");
        }
    }

    @Override
    public <T extends IPromotion> List<T> validFilter(Collection<T> promotions) {
        List<T> list = effectiveFilter(promotions);
        if(CollectionUtil.isEmpty(list)){
            return Lists.newArrayList();
        }
       return activityVerify.effectiveFilter(list);
    }

    private <T extends IPromotion> List<T> effectiveFilter(Collection<T> promotions){
        return promotions.stream().filter(T::getEnabled).filter(PromotionUtils::timeVerify).collect(Collectors.toList());
    }

}
