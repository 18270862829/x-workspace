package com.afiona.center.client.promotion.domain.aggregate;

import com.afiona.center.client.promotion.domain.model.promotionrule.seckill.SecondsKillRule;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 秒杀聚合根
 *
 * @author LiJinXing
 * @date 2020/4/7
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("秒杀聚合根")
public class SecondsKillAggregate extends PromotionAggregate{

    /**
     * 秒杀规则
     */
    private SecondsKillRule secondsKillRule;
}
