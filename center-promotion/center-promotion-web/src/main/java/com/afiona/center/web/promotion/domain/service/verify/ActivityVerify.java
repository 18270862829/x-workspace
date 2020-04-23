package com.afiona.center.web.promotion.domain.service.verify;

import com.afiona.center.client.promotion.domain.model.IPromotion;
import com.afiona.center.client.promotion.domain.model.Promotion;
import com.afiona.center.web.promotion.infrastructure.model.PromotionDO;

import java.util.Collection;
import java.util.List;

/**
 * 活动校验
 *
 * @author LiJinXing
 * @date 2020/4/15
 */
public interface ActivityVerify {

    <T extends IPromotion>List<T> effectiveFilter(Collection<T> promotions);
}
