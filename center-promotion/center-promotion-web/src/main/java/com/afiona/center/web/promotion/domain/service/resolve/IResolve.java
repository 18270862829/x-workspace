package com.afiona.center.web.promotion.domain.service.resolve;

import com.afiona.center.client.promotion.domain.aggregate.PromotionAggregate;

/**
 * 聚合根解析
 *
 * @author LiJinXing
 * @date 2020/4/22
 */
public interface IResolve<T extends PromotionAggregate> {

    /**
     * 解析聚合根
     *
     * @param promotionAggregate 促销聚合根
     * @return 促销id
     */
    Long resolve(T promotionAggregate);
}
