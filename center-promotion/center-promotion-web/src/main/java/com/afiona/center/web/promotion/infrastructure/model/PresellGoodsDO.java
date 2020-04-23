package com.afiona.center.web.promotion.infrastructure.model;

import com.afiona.center.client.promotion.util.IPromotion;
import com.afiona.common.model.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("promotion_presell_goods")
public class PresellGoodsDO extends SuperEntity  implements IPromotion {

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
