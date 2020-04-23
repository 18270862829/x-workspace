package com.afiona.center.web.promotion.domain.repo;

import com.afiona.center.client.promotion.domain.aggregate.FullReductionAggregate;
import com.afiona.common.pojo.JsonResult;

/**
 * 满减repo
 *
 * @author dengweiyi
 * @date 2020-02-09
 */
public interface FullReductionRepository extends BasePromotionRepository<FullReductionAggregate> {

    JsonResult removeFullReductionStairRule(Long id);
}
