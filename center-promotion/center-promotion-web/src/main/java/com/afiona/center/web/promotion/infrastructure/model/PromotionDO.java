package com.afiona.center.web.promotion.infrastructure.model;

import java.util.Date;

import com.afiona.center.client.promotion.constants.PromotionType;
import com.afiona.center.client.promotion.constants.StatusType;
import com.afiona.center.client.promotion.domain.model.IPromotion;
import com.afiona.center.client.promotion.util.PromotionUtils;
import com.afiona.common.model.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 促销活动DO
 *
 * @author dengweiyi
 * @date 2020-02-09
 */
@Data
@TableName("promotion")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class PromotionDO extends SuperEntity implements IPromotion<PromotionDO> {

    /**
     * 活动id
     */
    @ApiModelProperty("活动id")
    private Long activityId;

    /**
     * 名称
     */
    @ApiModelProperty("活动名称")
    private String name;

    /**
     * 促销编码
     */
    private String encoding;

    /**
     * 活动类型
     */
    private PromotionType type;

    /**
     * 活动目的
     */
    private String purpose;

    /**
     * false-停用，true-启用
     */
    private Boolean enabled;

    /**
     * 状态(0-未开始 1-已开始 2-已过期)
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
     * 活动描述
     */
    private String description;

    /**
     * 活动海报
     */
    private String posterUrl;

    /**
     * 活动优先级值
     */
    private Long priority;

    @Override
    public int compareTo(PromotionDO o) {
        return PromotionUtils.compareTo(this,o);
    }
}
