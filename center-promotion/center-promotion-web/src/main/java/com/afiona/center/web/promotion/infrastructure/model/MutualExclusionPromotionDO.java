package com.afiona.center.web.promotion.infrastructure.model;

import com.afiona.center.client.promotion.util.IPromotion;
import com.afiona.common.model.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 促销活动 互斥活动列表
 *
 * @author LiJinXing
 * @date 2020/2/27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("promotion_mutual_exclusion_promotion")
public class MutualExclusionPromotionDO extends SuperEntity implements IPromotion {

    /**
     * 促销活动ID
     */
    private Long promotionId;

    /**
     * 互斥的促销活动id
     */
    private Long exclusionId;
}
