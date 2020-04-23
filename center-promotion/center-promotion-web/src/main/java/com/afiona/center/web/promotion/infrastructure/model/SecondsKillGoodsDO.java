package com.afiona.center.web.promotion.infrastructure.model;

import com.afiona.center.client.promotion.util.IPromotion;
import com.afiona.common.model.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("promotion_seconds_kill_goods")
public class SecondsKillGoodsDO extends SuperEntity implements IPromotion {

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
