package com.afiona.center.client.promotion.domain.model.promotionrule.suit;

import com.afiona.common.model.SuperEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 互斥的促销活动
 *
 * @author LiJinXing
 * @date 2020/2/27
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("互斥的促销活动")
public class MutualExclusionPromotion extends SuperEntity {

    /**
     * 促销活动ID
     */
    private Long promotionId;

    /**
     * 互斥的促销活动id
     */
    private Long exclusionId;
}