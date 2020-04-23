package com.afiona.center.client.promotion.model.query;

import com.afiona.center.client.promotion.model.Goods;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * 支付有礼查询条件
 *
 * @author LiJinXing
 * @date 2020/3/19
 */
@Data
@Accessors(chain = true)
public class PayGiftBenefitCalcQuery {

    /**
     * 渠道ID
     */
    private long channelId;

    /**
     * 会员ID
     */
    private long memberId;

    /**
     * 关联商品列表
     */
    private List<Goods> goodsList;

}
