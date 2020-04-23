package com.afiona.center.web.promotion.domain.service.benefit;

import com.afiona.center.client.promotion.domain.model.Promotion;
import com.afiona.center.client.promotion.model.Goods;
import com.afiona.center.client.promotion.model.SingleBenefit;

/**
 * 单品福利计算
 *
 * @author LiJinXing
 * @date 2020/3/27
 */
public interface SingleBenefitCalcService {

    SingleBenefit calcBenefits(Promotion promotion, Goods goods);
}
