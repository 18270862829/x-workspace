package com.afiona.center.web.promotion.infrastructure.model;

import java.math.BigDecimal;
import java.util.Date;

import com.afiona.center.client.promotion.constants.GiveOutStatusEnum;
import com.deepexi.util.config.JsonDateSerializer;
import io.swagger.annotations.ApiModelProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.afiona.center.client.promotion.constants.CouponType;
import com.afiona.center.client.promotion.constants.choice.CouponUseTimeChoiceEnum;
import com.afiona.center.client.promotion.constants.choice.CoupondistributeChoiceEnum;
import com.afiona.center.client.promotion.util.IPromotion;
import com.afiona.common.model.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 优惠劵模版DO
 *
 * @author dengweiyi
 * @date 2020-02-09
 */
@Data
@TableName("promotion_coupon_template")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class CouponTemplateDO extends SuperEntity implements IPromotion {
    /**
     * 促销活动ID
     */
    private Long promotionId;

    /**
     * 优惠劵类型
     */
    private CouponType couponType;

    /**
     * 使用门槛（满x元）
     */
    private BigDecimal useThreshold;

    /**
     * 优惠内容（减免x元）
     */
    private BigDecimal reduceAmount;

    /**
     * 领取状态
     */
    private GiveOutStatusEnum giveOutStatus;

    /**
     * 优惠内容（折扣）
     */
    private BigDecimal discount;

    /**
     * 用劵时间选择
     */
    private CouponUseTimeChoiceEnum useTimeChoice;

    /**
     * 时间间隔-开始时间
     */
    @JsonSerialize( using = JsonDateSerializer.class )
    private Date startTime;

    /**
     * 时间间隔-结束时间
     */

    @JsonSerialize( using = JsonDateSerializer.class )
    private Date endTime;

    /**
     * 领劵当日起x天可用
     */
    private Integer theSameDayInterval;

    /**
     * 领劵次日起x天可用
     */
    private Integer theNextDayInterval;

    /**
     * 发劵方式选择
     */
    private CoupondistributeChoiceEnum distributeChoice;

    /**
     * 自动发放是否仍需手动领取
     */
    private Boolean autoTyIsByHand;

    /**
     * 发放限制 每人x张
     */
    private Integer giveOutLimit;

    /**
     * 领取限制 每人x张
     */
    private Integer receiveLimit;

    /**
     * 领取开始时间
     */
    private Date receiveStartTime;

    /**
     * 领取结束时间
     */
    private Date receiveEndTime;

    /**
     * 剩余数量
     */
    private Integer surplusNum;

    /**
     * 发放总量
     */
    private Integer total;
}
