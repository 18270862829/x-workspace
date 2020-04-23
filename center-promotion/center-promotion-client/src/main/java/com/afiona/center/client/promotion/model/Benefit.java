package com.afiona.center.client.promotion.model;

import com.afiona.center.client.promotion.constants.choice.DiscountsNumberEnum;
import com.afiona.center.client.promotion.domain.model.Promotion;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * 福利信息
 *
 * @author LiJinXing
 * @date 2020/3/27
 */
@Data
@Accessors(chain = true)
public class Benefit {

    /**
     * 活动基础信息
     */
    private Promotion promotion;

    /**
     * 单品优惠后的商品列表信息
     */
    private List<SingleBenefit> singleBenefitList;

    /**
     * 促销活动 优惠金额（不含单品促销活动优惠的金额）
     */
    private BigDecimal preferentialAmount;

    /**
     * 赠送积分
     */
    private int score;

    /**
     * 赠送优惠劵列表
     */
    private List<Long> couponTemplateIdList;

    /**
     * 赠品列表
     */
    private List<Long> giftSkuIds;

    /**
     * 包邮
     */
    private boolean freeShipping;

    /**
     * 换购数量-加价购
     */
    private DiscountsNumberEnum discountsNumberEnum;

    /**
     * 加购商品信息-加价购
     */
    private List<AddBuyGoods> addBuyGoodsList;
}
