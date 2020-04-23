package com.afiona.center.web.promotion.domain.service.resolve;

import com.afiona.center.client.promotion.domain.aggregate.PromotionAggregate;

import java.util.List;

/**
 *  促销基础解析器
 *
 * @author LiJinXing
 * @date 2020/4/22
 */
public interface IBaseResolve<T extends PromotionAggregate>  {

    /**
     * 解析聚合根
     *
     * @param promotionAggregate 促销聚合根
     * @return 促销id
     */
    Long resolve(T promotionAggregate);

    /**
     * 促销基础信息删除
     *
     * @param promotionIds 促销id列表
     */
    void removeBatchPromotion(List<Long> promotionIds);
}
