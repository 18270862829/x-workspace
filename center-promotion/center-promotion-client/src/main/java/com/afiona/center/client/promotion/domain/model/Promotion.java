package com.afiona.center.client.promotion.domain.model;


import java.util.*;

import cn.hutool.core.util.NumberUtil;
import com.afiona.center.client.promotion.constants.StatusType;
import com.afiona.center.client.promotion.util.PromotionUtils;
import com.deepexi.util.config.JsonDateSerializer;
import io.swagger.annotations.ApiOperation;
import lombok.EqualsAndHashCode;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.afiona.center.client.promotion.constants.PromotionType;
import com.afiona.common.model.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * 促销活动
 *
 * @author dengweiyi
 * @date 2020-02-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("promotion_campaign_info")
@Accessors(chain = true)
@ApiModel("促销活动基本信息")
public class Promotion extends SuperEntity implements IPromotion<Promotion> {

    public static Map<PromotionType,Integer> promotionMap=new HashMap<>();

    static {
        promotionMap.put(PromotionType.FIXED_PRICE,1);
        promotionMap.put(PromotionType.FULL_REDUCTION,2);
    }

    /**
     * 活动id
     */
    @ApiModelProperty("活动id")
    private Long activityId;

    /**
     * 促销编码
     */
    @ApiModelProperty("促销编码")
    private String encoding;

    /**
     * 名称
     */
    @ApiModelProperty("促销活动名称")
    private String name;

    /**
     * 活动名称
     */
    @ApiModelProperty("所属活动名称")
    private String activityName;

    /**
     * 活动目的
     */
    @ApiModelProperty("活动目的")
    private String purpose;

    /**
     * 活动类型（0-优惠券，1-满减/送，2-组合/套装，3-一口价,4-限时折扣，5-加价购，6-支付有礼）
     */
    @ApiModelProperty("活动类型（0-优惠券，1-满减/送，2-组合/套装，3-一口价,4-限时折扣，5-加价购，6-支付有礼）")
    private PromotionType type;

    /**
     * false-停用，true-启用
     */
    @ApiModelProperty("false-停用，true-启用")
    private Boolean enabled;

    /**
     * 状态(0-未开始 1-已开始 2-已过期)
     */
    @ApiModelProperty("状态(0-未开始 1-已开始 2-已过期)")
    private StatusType status;

    /**
     * 开始时间
     */
    @JsonSerialize( using = JsonDateSerializer.class)
    @ApiModelProperty("开始时间")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonSerialize( using = JsonDateSerializer.class)
    @ApiModelProperty("结束时间")
    private Date endTime;

    /**
     * 活动描述
     */
    @ApiModelProperty("活动描述")
    private String description;

    /**
     * 活动海报
     */
    @ApiModelProperty("活动海报")
    private String posterUrl;

    /**
     * 活动优先级值
     */
    @ApiModelProperty("活动优先级值")
    private Long priority;

    @Override
    public int compareTo(Promotion o) {
        return PromotionUtils.compareTo(this,o);
    }
}
