package com.afiona.center.web.promotion.infrastructure.model;

import com.afiona.center.client.promotion.util.IPromotion;
import com.afiona.common.model.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 满减促销活动关联赠品DO
 *
 * @author dengweiyi
 * @date 2020-02-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("promotion_full_reduction_git_goods")
public class FullReductionGitGoodsDO extends SuperEntity implements IPromotion{
    /**
     * 促销活动ID
     */
    private Long promotionId;

    /**
     * 阶段满减规则ID
     */
    private Long stairRuleId;

    /**
     * 赠品ID
     */
    private Long skuId;
}
