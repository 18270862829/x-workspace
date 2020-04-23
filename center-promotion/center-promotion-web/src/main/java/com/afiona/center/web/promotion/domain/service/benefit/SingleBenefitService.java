package com.afiona.center.web.promotion.domain.service.benefit;

import com.afiona.center.client.promotion.domain.model.Promotion;
import com.afiona.center.client.promotion.model.Goods;
import com.afiona.center.client.promotion.model.PromotionGroup;
import com.afiona.center.client.promotion.model.SingleBenefit;
import com.afiona.center.client.promotion.model.SingleCommoditiesBenefit;
import com.afiona.common.pojo.JsonResult;

import java.util.List;

/**
 * 单个商品福利计算service
 *
 * @author LiJinXing
 * @date 2020/3/27
 */
public interface SingleBenefitService {


    JsonResult<List<SingleCommoditiesBenefit>> benefitCalc(List<PromotionGroup> promotionGroups, List<Goods> goodsList, Long channelId, Long memberId);

    SingleBenefit singlePromotionBenefit(Goods goods, Promotion promotion, Long channelId, Long memberId);
}
