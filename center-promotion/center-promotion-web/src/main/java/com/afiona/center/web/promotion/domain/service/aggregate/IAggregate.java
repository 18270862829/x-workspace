package com.afiona.center.web.promotion.domain.service.aggregate;

import com.afiona.center.client.promotion.domain.aggregate.PromotionAggregate;

import java.util.List;

/**
 * 聚合根聚合
 *
 * @author LiJinXing
 * @date 2020/4/22
 */
public interface IAggregate<T extends PromotionAggregate> {

    /**
     * 聚合
     * promotionAggregate至少需要包含促销id，且不能为null
     *
     * @param promotionAggregate 聚合根信息
     */
    void aggregate(List<T> promotionAggregate);
}
