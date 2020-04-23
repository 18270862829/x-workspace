package com.afiona.center.client.promotion.domain.model.promotionrule.addmoneytobuy;

import com.afiona.center.client.promotion.constants.choice.DiscountsNumberEnum;
import com.afiona.center.client.promotion.constants.choice.FullNumChoiceEnum;
import com.afiona.center.client.promotion.constants.choice.FullPriceChoiceEnum;
import com.afiona.center.client.promotion.constants.choice.ThresholdChoiceEnum;
import com.afiona.common.model.SuperEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * 加价购规则
 *
 * @author LiJinXing
 * @date 2020/2/22
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("加价购规则")
public class AddMoneyToBuyRule extends SuperEntity {

    /**
     * 促销活动ID
     */
    private Long promotionId;

    /**
     * 优惠门槛选择
     */
    private ThresholdChoiceEnum thresholdChoice;

    /**
     * 满x元
     */
    private BigDecimal fullAmount;

    /**
     * 满x件
     */
    private Integer fullNum;

    /**
     * 满x件条件选择
     */
    private FullNumChoiceEnum fullNumChoice;

    /**
     * 换购数量
     */
    private DiscountsNumberEnum discountsNumber;

    /**
     * 换购商品
     */
    private List<AddMoneyToBuyGoods> addMoneyToBuyGoodsList;
}
