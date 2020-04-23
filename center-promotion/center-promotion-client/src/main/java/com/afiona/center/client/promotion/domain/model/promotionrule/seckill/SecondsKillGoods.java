package com.afiona.center.client.promotion.domain.model.promotionrule.seckill;

import com.afiona.common.model.SuperEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 秒杀-参与商品
 *
 * @author LiJinXing
 * @date 2020/4/7
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SecondsKillGoods extends SuperEntity {

    /**
     * 促销活动id
     */
    private Long promotionId;

    /**
     * 商品skuId
     */
    private Long skuId;

    /**
     * 减x元
     */
    private BigDecimal amount;

    /**
     * 秒杀库存
     */
    private Long inventory;
}
