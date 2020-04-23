package com.afiona.center.client.promotion.model.query;

import com.afiona.center.client.promotion.constants.StatusType;
import com.afiona.center.client.promotion.constants.choice.ActivityStatus;
import com.afiona.center.client.promotion.util.RangeUtil;
import com.afiona.common.pojo.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 活动条件查询
 *
 * @author LiJinXing
 * @date 2020/3/4
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class ActivityQuery extends PageQuery {

    /**
     * 活动名称
     */
    private String name;

    /**
     * 活动目的
     */
    private String purpose;

    /**
     * 状态(0-未开始 1-已开始 2-已结束)
     */
    private List<StatusType> status;

    /**
     * 活动时间
     */
    private RangeUtil.TimeRange timeRange;

    /**
     * 活动id
     */
    private List<Long> activityIds;
}
