package com.afiona.center.web.promotion.infrastructure.model;

import com.afiona.center.client.promotion.constants.StatusType;
import com.afiona.center.client.promotion.constants.choice.ActivityStatus;
import com.afiona.common.model.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 活动DO
 *
 * @author LiJinXing
 * @date 2020/3/4
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("promotion_activity")
public class ActivityDO extends SuperEntity {

    /**
     * 活动名称
     */
    @ApiModelProperty("活动名称")
    private String name;

    /**
     * 活动编码
     */
    @ApiModelProperty("活动编码")
    private String encoding;

    /**
     * 创建者的id
     */
    private Long creatorId;

    /**
     * false-停用，true-启用
     */
    private Boolean enabled;

    /**
     * 状态(0-未开始 1-已开始 2-已结束)
     */
    private StatusType status;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 活动目的
     */
    private String activityPurpose;

    /**
     * 活动描述
     */
    private String description;
}
