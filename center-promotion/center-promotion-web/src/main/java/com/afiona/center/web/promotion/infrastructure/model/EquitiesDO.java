package com.afiona.center.web.promotion.infrastructure.model;

import com.afiona.center.client.promotion.constants.choice.MemberDiscountsTypeEnum;
import com.afiona.center.client.promotion.constants.choice.SuperimposedTypeEnum;
import com.afiona.center.client.promotion.domain.model.promotionrule.suit.MutualExclusionPromotion;
import com.afiona.center.client.promotion.util.IPromotion;
import com.afiona.common.model.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * TODO
 *
 * @author LiJinXing
 * @date 2020/4/10
 */
@Data
@TableName("promotion_equities")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class EquitiesDO  extends SuperEntity implements IPromotion {

    /**
     * 促销活动ID
     */
    private Long promotionId;

    /**
     * 叠加类型
     */
    private SuperimposedTypeEnum superimposedType;

    /**
     * 会员权益类型
     */
    private MemberDiscountsTypeEnum memberDiscountsType;

}
