package com.afiona.center.client.promotion.model.query;

import com.afiona.center.client.promotion.constants.PromotionType;
import com.afiona.center.client.promotion.domain.model.Promotion;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 使用活动类型的活动信息查询
 *
 * @author LiJinXing
 * @date 2020/3/27
 */
@Data
@Accessors(chain = true)
public class SuitPromotionQuery {

    private List<Long> promotionIds;

    private List<PromotionType> promotionTypes;
}
