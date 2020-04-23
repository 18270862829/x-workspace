package com.afiona.center.client.promotion.domain.model.promotionrule.presell;

import com.afiona.center.client.promotion.constants.PresellType;
import com.afiona.center.client.promotion.domain.aggregate.PromotionAggregate;
import com.afiona.common.model.SuperEntity;
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
@Accessors(chain = true)
@ApiModel("预售规则")
public class PresellRule extends SuperEntity {

    @ApiModelProperty("促销活动id")
    private Long promotionId;

    @ApiModelProperty("预售类型")
    private PresellType presellType;

    @ApiModelProperty("首款时间")
    private Date firstTime;

    @ApiModelProperty("尾款时间")
    private Date lastTime;

    @ApiModelProperty("预售商品列表")
    private List<PresellGoods> presellGoodsList;
}
