package com.afiona.center.client.promotion.domain.model.suitrule;

import cn.hutool.core.collection.CollectionUtil;
import com.afiona.center.client.promotion.constants.choice.LimitTypeChoiceEnum;
import com.afiona.center.client.promotion.constants.choice.MemberDiscountsTypeEnum;
import com.afiona.center.client.promotion.constants.choice.SuitChoiceEnum;
import com.afiona.center.client.promotion.constants.choice.SuperimposedTypeEnum;
import com.afiona.center.client.promotion.domain.model.promotionrule.suit.MutualExclusionPromotion;
import com.afiona.common.model.SuperEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.afiona.center.client.promotion.util.GenericHelper.*;

/**
 * 适用规则选择
 *
 * @author dengweiyi
 * @date 2020-02-05
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("适用规则选择")
public class SuitRule extends SuperEntity {

    /**
     * 促销活动ID
     */
    private Long promotionId;

    /**
     * 适用渠道选择
     */
    private SuitChoiceEnum suitChannelChoice;

    /**
     * 适用对象选择
     */
    private SuitChoiceEnum suitMemberChoice;

    /**
     * 适用商品选择
     */
    private SuitChoiceEnum suitGoodsChoice;

    /**
     * 限购次数
     */
    private Integer limitTimes;

    /**
     * 限购类型
     */
    private LimitTypeChoiceEnum limitTypeChoice;

    /**
     * 适用渠道列表
     */
    private List<SuitChannel> suitChannels;

    /**
     * 适用对象列表
     */
    private List<SuitMember> suitMembers;

    /**
     * 适用商品列表
     */
    private List<SuitGoods> suitGoods;


    public boolean channelQualified(long channelId){
        return hasIdInList(suitChannels, SuitChannel::getChannelId, channelId);
    }

    public boolean memberQualified(long memberId){
        return hasIdInList(suitMembers, SuitMember::getMemberId, memberId);
    }

    public boolean goodsQualified(List<Long> skuIds){
        if(CollectionUtil.isEmpty(suitGoods) || CollectionUtil.isEmpty(skuIds)){
            return true;
        }
        Set<Long> skuIdSet = suitGoods.stream().map(SuitGoods::getSkuId).collect(Collectors.toSet());
        skuIdSet.retainAll(skuIds);
        return skuIdSet.size() == skuIds.size();
    }
}
