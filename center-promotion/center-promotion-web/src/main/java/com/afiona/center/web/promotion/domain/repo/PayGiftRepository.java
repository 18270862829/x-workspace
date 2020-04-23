package com.afiona.center.web.promotion.domain.repo;

import com.afiona.center.client.promotion.domain.aggregate.PayGiftAggregate;
import com.afiona.common.pojo.JsonResult;

import java.util.List;

/**
 * 支付有礼repo
 *
 * @author LiJinXing
 * @date 2020/2/25
 */
public interface PayGiftRepository extends BasePromotionRepository<PayGiftAggregate> {
    JsonResult removePayGiftStairRuleIds(List<Long> payGiftStairRuleIds);
}
