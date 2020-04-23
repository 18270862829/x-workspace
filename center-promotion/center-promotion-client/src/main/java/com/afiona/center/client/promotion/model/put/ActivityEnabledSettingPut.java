package com.afiona.center.client.promotion.model.put;

import lombok.Data;
import javax.validation.constraints.NotNull;
/**
 * 活动启动或停用条件
 *
 * @author LiJinXing
 * @date 2020/3/26
 */
@Data
public class ActivityEnabledSettingPut {

    @NotNull(message = "活动id不能是null")
    private Long activityId;

    @NotNull(message = "enabled必须是true或这false")
    private Boolean enabled;
}
