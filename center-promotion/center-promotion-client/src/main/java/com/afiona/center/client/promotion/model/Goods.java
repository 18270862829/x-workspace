package com.afiona.center.client.promotion.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 商品信息
 *
 * @author LiJinXing
 * @date 2020/3/27
 */
@Data
@Accessors(chain = true)
public class Goods {

    /**
     * skuId
     */
    private Long skuId;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * sku数量
     */
    private Integer num;
}
