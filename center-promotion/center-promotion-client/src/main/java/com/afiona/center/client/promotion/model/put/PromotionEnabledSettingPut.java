package com.afiona.center.client.promotion.model.put;

import lombok.Data;

/**
 * 活动停用启用修改条件
 *
 * @author LiJinXing
 * @date 2020/3/26
 */
@Data
public class PromotionEnabledSettingPut {

    private Long promotionId;

    private Boolean enabled;
}
