package com.afiona.center.web.promotion.infrastructure.model;

import com.afiona.center.client.promotion.constants.PresellType;
import com.afiona.center.client.promotion.domain.aggregate.PromotionAggregate;
import com.afiona.center.client.promotion.util.IPromotion;
import com.afiona.common.model.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * 预售规则
 *
 * @author LiJinXing
 * @date 2020/4/22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("promotion_presell_rule")
public class PresellRuleDO extends SuperEntity  implements IPromotion {

    @ApiModelProperty("促销id")
    private Long promotionId;

    @ApiModelProperty("预售类型")
    private PresellType presellType;

    @ApiModelProperty("首款时间")
    private Date firstTime;

    @ApiModelProperty("尾款时间")
    private Date lastTime;

}
