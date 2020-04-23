package com.afiona.center.web.promotion.domain.service.verify;


import com.afiona.center.client.promotion.constants.choice.OperatingTypeEnum;
import com.afiona.center.client.promotion.domain.model.IPromotion;

import java.util.Collection;
import java.util.List;

/**
 * TODO
 *
 * @author LiJinXing
 * @date 2020/4/15
 */
public interface PromotionVerify {

    void statusVerify(Long promotion, OperatingTypeEnum operatingType);

    <T extends IPromotion> List<T> validFilter(Collection<T> promotions);

}
