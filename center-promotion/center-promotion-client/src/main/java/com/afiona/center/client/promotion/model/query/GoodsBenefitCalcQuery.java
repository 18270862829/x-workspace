package com.afiona.center.client.promotion.model.query;

import com.afiona.center.client.promotion.model.Goods;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品福利计算
 *
 * @author LiJinXing
 * @date 2020/3/19
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class GoodsBenefitCalcQuery {

    /**
     * 会员id
     */
    private Long memberId;

    /**
     * 渠道id
     */
    private Long channelId;

    /**
     * 单品促销id
     */
    private Long simplePromotionId;

    /**
     * 关联商品列表
     */
    private List<Goods> goodsList;

}
