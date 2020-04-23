package com.afiona.center.client.promotion.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 使用规则的商品信息
 *
 * @author LiJinXing
 * @date 2020/3/11
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SuitRuleGoods {

    /**
     * 商品id
     */
    private Long skuId;

    /**
     * 商品原价
     */
    private BigDecimal price;

}
