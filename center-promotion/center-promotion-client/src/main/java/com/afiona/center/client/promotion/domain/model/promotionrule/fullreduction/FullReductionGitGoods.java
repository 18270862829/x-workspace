package com.afiona.center.client.promotion.domain.model.promotionrule.fullreduction;

import com.afiona.center.client.promotion.model.PromotionStairRuleKey;
import com.afiona.common.model.SuperEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 满减促销活动关联赠品
 *
 * @author dengweiyi
 * @date 2020-02-06
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("满减促销活动关联赠品")
public class FullReductionGitGoods extends SuperEntity {
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

    public PromotionStairRuleKey getKey(){
        return new PromotionStairRuleKey(promotionId, stairRuleId);
    }
}
