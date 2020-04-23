package com.afiona.center.client.promotion.model.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 促销适用商品查询条件
 *
 * @author LiJinXing
 * @date 2020/4/14
 */
@Data
@Accessors(chain = true)
public class PromotionSuitGoodsQuery {

    @ApiModelProperty("渠道id")
    private Long channelId;

    @ApiModelProperty("会员id")
    private Long memberId;

    @ApiModelProperty("促销id")
    private Long promotionId;
}
