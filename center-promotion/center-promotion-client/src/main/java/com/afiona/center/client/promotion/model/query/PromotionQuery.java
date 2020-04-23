package com.afiona.center.client.promotion.model.query;

import com.afiona.center.client.promotion.constants.PromotionType;
import com.afiona.center.client.promotion.constants.StatusType;
import com.afiona.common.pojo.PageRequest;
import com.deepexi.util.config.JsonDateSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author clb
 * @date 2020/01/08
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class PromotionQuery extends PageRequest {

    /**
     * 活动id
     */
    @ApiModelProperty("活动id")
    private List<Long> activityIds;

    /**
     * 活动名称
     */
    @ApiModelProperty("促销活动ID")
    private String name;

    /**
     * 活动类型
     */
    @ApiModelProperty("活动类型")
    private List<PromotionType> promotionTypes;

    /**
     * 活动目的
     */
    @ApiModelProperty("活动目的")
    private String purpose;

    /**
     * 活动编码
     */
    @ApiModelProperty("活动编码")
    private String encoding;

    /**
     * 状态(0-未开始 1-已开始 2-已过期)
     */
    @ApiModelProperty("状态(0-未开始 1-已开始 2-已过期)")
    private StatusType status;

    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date endTime;

    /**
     * SKU ID
     */
    @ApiModelProperty("SKU ID")
    private List<Long> skuIds;

    /**
     * 活动id
     */
    @ApiModelProperty("活动id")
    private List<Long> promotionIds;

}
