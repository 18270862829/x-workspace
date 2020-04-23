package com.afiona.center.client.promotion.domain.model.promotionrule.coupon;

import java.math.BigDecimal;
import java.util.Date;

import com.afiona.center.client.promotion.constants.GiveOutStatusEnum;
import com.deepexi.util.config.JsonDateSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.afiona.center.client.promotion.constants.CouponType;
import com.afiona.center.client.promotion.constants.choice.CouponUseTimeChoiceEnum;
import com.afiona.center.client.promotion.constants.choice.CoupondistributeChoiceEnum;
import com.afiona.center.client.promotion.util.RangeUtil;
import com.afiona.common.model.SuperEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 优惠劵模版
 *
 * @author dengweiyi
 * @date 2020-02-06
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("优惠劵模版")
public class CouponTemplate extends SuperEntity {

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
     * 优惠内容（折扣）
     */
    private BigDecimal discount;

    /**
     * 用劵时间
     */
    private CouponUseTime couponUseTime;

    /**
     * 领取状态
     */
    @ApiModelProperty("领取状态")
    private GiveOutStatusEnum giveOutStatus;

    /**
     * 发劵方式
     */
    private CouponDistribute couponDistribute;

    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = false)
    public static class CouponUseTime{
        /**
         * 用劵时间选择
         */
        private CouponUseTimeChoiceEnum useTimeChoice;

        /**
         * 时间间隔
         */
        private RangeUtil.TimeRange timeRange;

        /**
         * 领劵当日起x天可用
         */
        private Integer theSameDayInterval;

        /**
         * 领劵次日起x天可用
         */
        private Integer theNextDayInterval;
    }

    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = false)
    public static class CouponDistribute{

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
        @JsonSerialize( using = JsonDateSerializer.class )
        private Date receiveStartTime;

        /**
         * 领取结束时间
         */
        @JsonSerialize( using = JsonDateSerializer.class )
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

    /**
     * 是否达到门槛
     *
     * @param price
     * @return : boolean
     */
    public boolean achieveThreshold(BigDecimal price){
        if(useThreshold == null || price == null){
            return false;
        }
        return price.compareTo(useThreshold) >= 0;
    }

    /**
     * 计算优惠金额
     *
     * @param
     * @return : java.math.BigDecimal
     */
    public BigDecimal calcPreferentialAmount(BigDecimal price){
        if(price == null){
            return BigDecimal.ZERO;
        }
        if(couponType == CouponType.DISCOUNT){
            if(discount == null){
                return BigDecimal.ZERO;
            }
            BigDecimal discountAmount = price.multiply(discount);
            return price.subtract(discountAmount);
        }else{
            if(reduceAmount == null){
                return BigDecimal.ZERO;
            }
            return reduceAmount;
        }
    }
}
