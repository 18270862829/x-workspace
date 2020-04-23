package com.afiona.center.client.promotion.domain.model.promotionrule.presell;

import com.afiona.common.model.SuperEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 预售商品
 *
 * @author LiJinXing
 * @date 2020/4/22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("预售商品")
public class PresellGoods extends SuperEntity {

    @ApiModelProperty("促销id")
    private Long promotionId;

    @ApiModelProperty("商品skuId")
    private Long skuId;

    @ApiModelProperty("预售价格")
    private BigDecimal price;

    @ApiModelProperty("预售库存")
    private Integer inventory;

    @ApiModelProperty("预售定金")
    private BigDecimal  earnestMoney;

    @ApiModelProperty("膨胀系数")
    private BigDecimal swellFactor;

    @ApiModelProperty("预计发货时间")
    private Date estimatedShipDate;
}
