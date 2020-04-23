package com.afiona.center.web.promotion.domain.service.aggregate;

import com.afiona.center.client.promotion.domain.aggregate.PayGiftAggregate;
import com.afiona.center.client.promotion.domain.aggregate.PromotionAggregate;

import java.util.List;

/**
 * 促销基础信息聚合器
 *
 * @author LiJinXing
 * @date 2020/4/22
 */
public interface IBaseAggregate<T extends PromotionAggregate> {

    /**
     * 促销基础信息聚合，包含权益，适用规则，和促销基本信息
     *
     * @param promotionAggregate 促销聚合根
     */
    void aggregate(List<T> promotionAggregate);
}
