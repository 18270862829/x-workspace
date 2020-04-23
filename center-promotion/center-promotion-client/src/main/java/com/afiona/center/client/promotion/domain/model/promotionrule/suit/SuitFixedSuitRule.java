package com.afiona.center.client.promotion.domain.model.promotionrule.suit;

import com.afiona.common.model.SuperEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * 固定套装规则
 *
 * @author LiJinXing
 * @date 2020/4/9
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("固定套装规则")
public class SuitFixedSuitRule extends SuperEntity {

    /**
     * 促销活动ID
     */
    private Long promotionId;

    /**
     * 套装名称
     */
    private String name;

    /**
     * 套装编码
     */
    private String encoding;

    /**
     * 套装描述
     */
    private String description;

    /**
     * 套装价格
     */
    private BigDecimal suitPrice;

    /**
     * 固定套装关联商品信息
     */
    private List<SuitFixedSuitGoods> fixedSuitGoodsList;

    /**
     * 商品主图路径
     */
    private String goodsPicUrl;
}
