package com.afiona.center.web.promotion.infrastructure.model;

import com.afiona.center.client.promotion.util.IPromotion;
import com.afiona.common.model.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

/**
 * 固定套装规则
 *
 * @author LiJinXing
 * @date 2020/4/9
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("promotion_suit_fixed_suit_rule")
public class SuitFixedSuitRuleDO extends SuperEntity implements IPromotion {

    /**
     * 促销活动ID
     */
    private Long promotionId;

    /**
     * 套装名称
     */
    private String name;

    /**
     * 套装编码
     */
    private String encoding;

    /**
     * 套装描述
     */
    private String description;

    /**
     * 套装价格
     */
    private BigDecimal suitPrice;

    /**
     * 商品主图路径
     */
    private String goodsPicUrl;
}
