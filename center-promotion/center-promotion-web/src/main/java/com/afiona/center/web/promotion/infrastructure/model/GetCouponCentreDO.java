package com.afiona.center.web.promotion.infrastructure.model;

import com.afiona.common.model.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 领券 中心DO
 *
 * @author LiJinXing
 * @date 2020/3/5
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("promotion_get_coupon_centre")
public class GetCouponCentreDO extends SuperEntity {

    /**
     * 优惠券模板id
     */
    private Long couponTemplateId;

    /**
     * 会员id
     */
    private Long memberId;

    /**
     * 领取状态（true-已领取，false-未领取）
     */
    private Boolean receiveState;

    /**
     * 领取开始时间
     */
    private Date receiveStartTime;

    /**
     * 领取结束时间
     */
    private Date receiveEndTime;

}
