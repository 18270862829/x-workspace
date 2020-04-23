package com.afiona.center.web.promotion.domain.service.benefit.verify;

import com.afiona.center.client.promotion.constants.choice.SuitChoiceEnum;
import com.afiona.center.client.promotion.constants.choice.SuperimposedTypeEnum;
import com.afiona.center.client.promotion.domain.model.promotionrule.suit.MutualExclusionPromotion;
import com.afiona.center.client.promotion.domain.model.suitrule.SuitChannel;
import com.afiona.center.client.promotion.domain.model.suitrule.SuitGoods;
import com.afiona.center.client.promotion.domain.model.suitrule.SuitMember;
import com.afiona.center.client.promotion.domain.model.suitrule.SuitRule;
import com.afiona.center.client.promotion.model.Goods;
import com.afiona.center.client.promotion.model.SuitRuleGoods;
import com.afiona.center.client.promotion.model.query.BenefitCalcQuery;
import com.afiona.common.util.CloneUtil;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 使用规则校验
 *
 * @author LiJinXing
 * @date 2020/3/11
 */
public class SuitRuleVerify {

    public static Boolean suitRuleGoodsVerify(SuitRule suitRule, Long skuId){

        if(SuitChoiceEnum.ALL.equals(suitRule.getSuitGoodsChoice())){
            return true;
        }
        //适用商品列表
        Set<Long> goodsIds = suitRule.getSuitGoods() == null ? null : suitRule.getSuitGoods().stream().map(SuitGoods::getSkuId).collect(Collectors.toSet());

           //指定适用
        if(SuitChoiceEnum.SUIT.equals(suitRule.getSuitGoodsChoice())){
            if(goodsIds.contains(skuId)){
                return true;
            }
        }
        //指定不适用
        if(SuitChoiceEnum.NOT_SUIT.equals(suitRule.getSuitGoodsChoice())){
            if(!goodsIds.contains(skuId)){
               return true;
            }
        }

          return false;
    }

    public static Boolean suitChannelVerify(SuitRule suitRule,Long channelId){
        if(suitRule.getSuitChannelChoice().equals(SuitChoiceEnum.ALL)){
            return true;
        }
        //适用渠道列表
        Set<Long> channelIds = suitRule.getSuitChannels().stream().map(SuitChannel::getChannelId).collect(Collectors.toSet());
        if(suitRule.getSuitChannelChoice().equals(SuitChoiceEnum.NOT_SUIT)){
            return !channelIds.contains(channelId);

        }
        if(suitRule.getSuitChannelChoice().equals(SuitChoiceEnum.SUIT)){
            return channelIds.contains(channelId);
        }
        return true;
    }

    public static Boolean suitMemberVerify(SuitRule suitRule,Long memberId){
        if(suitRule.getSuitMemberChoice().equals(SuitChoiceEnum.ALL)){
            return true;
        }
        //适用渠道列表
        Set<Long> memberIds = suitRule.getSuitMembers().stream().map(SuitMember::getMemberId).collect(Collectors.toSet());
        if(suitRule.getSuitGoodsChoice().equals(SuitChoiceEnum.NOT_SUIT)){
            return !memberIds.contains(memberId);
        }
        else if(suitRule.getSuitGoodsChoice().equals(SuitChoiceEnum.SUIT)){
            return memberIds.contains(memberId);
        }
        return true;
    }

}
