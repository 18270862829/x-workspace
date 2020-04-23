package com.afiona.center.client.promotion.domain.model.equities;

import com.afiona.center.client.promotion.constants.choice.MemberDiscountsTypeEnum;
import com.afiona.center.client.promotion.constants.choice.SuperimposedTypeEnum;
import com.afiona.center.client.promotion.domain.model.promotionrule.suit.MutualExclusionPromotion;
import com.afiona.common.model.SuperEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

import static com.afiona.center.client.promotion.util.GenericHelper.hasIdInList;

/**
 * 权益
 *
 * @author LiJinXing
 * @date 2020/4/10
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("适用规则选择")
public class Equities  extends SuperEntity {

    /**
     * 促销活动ID
     */
    private Long promotionId;

    /**
     * 叠加类型
     */
    @ApiModelProperty("叠加类型")
    private SuperimposedTypeEnum superimposedType;

    /**
     * 会员权益类型
     */
    @ApiModelProperty("会员权益类型")
    private MemberDiscountsTypeEnum memberDiscountsType;

    /**
     * 互斥活动
     */
    @ApiModelProperty("互斥活动")
    private List<MutualExclusionPromotion> mutualExclusionPromotions;

    public boolean mutualExclusionPromotionsQualified(Long exclusionId){
        return hasIdInList(mutualExclusionPromotions,MutualExclusionPromotion::getExclusionId,exclusionId);
    }
}
