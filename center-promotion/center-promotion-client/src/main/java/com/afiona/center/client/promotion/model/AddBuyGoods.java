package com.afiona.center.client.promotion.model;

import com.afiona.center.client.promotion.constants.choice.DiscountsNumberEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 加购商品
 *
 * @author LiJinXing
 * @date 2020/3/29
 */
@Data
@Accessors(chain = true)
public class AddBuyGoods {

    /**
     * SKU ID
     */
    private Long skuId;

    /**
     * 加购价
     */
    private BigDecimal discountsPrice;

}
