package com.afiona.center.client.promotion.domain.model.suitrule;

import com.afiona.common.model.SuperEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 适用对象
 *
 * @author dengweiyi
 * @date 2020-02-05
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("适用对象")
public class SuitMember extends SuperEntity {

    /**
     * 促销活动ID
     */
    private Long promotionId;

    /**
     * 会员ID
     */
    private Long memberId;
}
