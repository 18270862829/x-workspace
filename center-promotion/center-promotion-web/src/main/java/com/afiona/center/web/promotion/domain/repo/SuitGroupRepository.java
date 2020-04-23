package com.afiona.center.web.promotion.domain.repo;

import com.afiona.center.client.promotion.domain.aggregate.SuitGroupAggregate;
import com.afiona.common.pojo.JsonResult;

/**
 * 组合套装repo
 *
 * @author LiJinXing
 * @date 2020/2/19
 */
public interface SuitGroupRepository extends BasePromotionRepository<SuitGroupAggregate> {
    JsonResult removeSuitFixedSuitRule(Long suitFixedSuitRuleId);

    JsonResult removeSuitFixedSuitGoods(Long suitFixedSuitGoodsId);
}
