package com.afiona.center.client.promotion.domain.model;

import java.util.Date;

import com.afiona.center.client.promotion.constants.StatusType;
import com.afiona.center.client.promotion.constants.choice.ActivityStatus;
import com.deepexi.util.config.JsonDateSerializer;
import io.swagger.annotations.ApiModel;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.afiona.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 活动model
 *
 * @author LiJinXing
 * @date 2020/3/4
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("活动model")
public class Activity extends SuperEntity {

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
    @JsonSerialize( using = JsonDateSerializer.class )
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonSerialize( using = JsonDateSerializer.class )
    private Date endTime;

    /**
     * 促销数量
     */
    private Integer promotionNum;

    /**
     * 活动目的
     */
    private String activityPurpose;

    /**
     * 活动描述
     */
    private String description;
}
