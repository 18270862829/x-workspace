package com.afiona.center.client.promotion.model;

import com.afiona.center.client.promotion.domain.model.Promotion;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * 每个商品的福利
 *
 * @author LiJinXing
 * @date 2020/3/19
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SingleCommoditiesBenefit{

    /**
     * 商品 skuId
     */
    private Long skuId;

    /**
     * 单品优惠后的商品列表信息
     */
    private List<SingleBenefit> singleBenefitList;
}
