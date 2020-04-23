package com.afiona.center.client.promotion.model;

import com.afiona.center.client.promotion.constants.choice.DiscountsNumberEnum;
import com.afiona.center.client.promotion.domain.model.promotionrule.addmoneytobuy.AddMoneyToBuyGoods;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 福利
 *
 * @author LiJinXing
 * @date 2020/3/26
 */
@Data
@Accessors(chain = true)
public class MaterialBenefits {

    /**
     * 福利
     */
    private List<Benefit> benefitList;

}
