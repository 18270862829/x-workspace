package com.afiona.center.client.promotion.model.query;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * 福利计算查询
 *
 * @author dengweiyi
 * @date 2020-02-10
 */
@Data
@Accessors(chain = true)
public class BenefitCalcQuery {

    /**
     * 渠道ID
     */
    private long channelId;

    /**
     * 会员ID
     */
    private long memberId;

    /**
     * 涉及促销活动列表
     */
    private List<InvolvedPromotion> involvedPromotions;

    @Data
    @Accessors(chain = true)
    public static class InvolvedPromotion{

        /**
         * 促销活动ID
         */
        private long promotionId;

        /**
         * 会员参与该促销活动次数
         */
        private int memberParticipationTimes;

        /**
         * 关联商品列表
         */
        private List<RelatedGoods> relatedGoods;
    }

    @Data
    @Accessors(chain = true)
    public static class RelatedGoods{
        /**
         * SKU ID
         */
        private long skuId;

        /**
         * 价格
         */
        private BigDecimal price;

        /**
         * sku数量
         */
        private Integer num;

        /**
         * 单品促销活动
         */
        private Long singlePromotionId;
    }
}
