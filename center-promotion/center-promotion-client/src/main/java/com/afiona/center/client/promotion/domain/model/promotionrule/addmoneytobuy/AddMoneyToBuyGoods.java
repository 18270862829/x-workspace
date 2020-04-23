package com.afiona.center.client.promotion.domain.model.promotionrule.addmoneytobuy;

import com.afiona.common.model.SuperEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 换购商品
 *
 * @author LiJinXing
 * @date 2020/2/22
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("换购商品")
public class AddMoneyToBuyGoods extends SuperEntity {

    /**
     * 促销活动ID
     */
    private Long promotionId;

    /**
     * SKU ID
     */
    private Long skuId;

    /**
     * 加购价
     */
    private BigDecimal discountsPrice;
}
