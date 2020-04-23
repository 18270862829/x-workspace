package com.afiona.center.client.promotion.model;

import com.afiona.center.client.promotion.domain.model.Promotion;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * 单商品单品促销福利福利
 *
 * @author LiJinXing
 * @date 2020/3/27
 */
@Data
@Accessors(chain = true)
public class SingleBenefit implements Comparable<SingleBenefit>{

    /**
     * 商品skuId
     */
    private Long skuId;

    /**
     * 活动基础信息
     */
    private Promotion promotion;

    /**
     * 单品促销活动 优惠金额
     */
    private BigDecimal preferentialAmount;

    /**
     * 单品原价
     */
    private BigDecimal price;

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

    @Override
    public int compareTo(SingleBenefit o) {

        if(o.getPreferentialAmount()!=null&&this.getPreferentialAmount()!=null){
            return o.preferentialAmount.compareTo(this.preferentialAmount);
        }
        return this.getPreferentialAmount()==null?-1:1;
    }
}
