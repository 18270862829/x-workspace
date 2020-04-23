package com.afiona.center.web.promotion.domain.service.aggregate.impl;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.afiona.center.client.promotion.domain.aggregate.AddMoneyToBuyAggregate;
import com.afiona.center.client.promotion.domain.aggregate.CouponTemplateAggregate;
import com.afiona.center.client.promotion.domain.model.Promotion;
import com.afiona.center.client.promotion.domain.model.equities.Equities;
import com.afiona.center.web.promotion.domain.service.aggregate.IBaseAggregate;
import com.afiona.center.web.promotion.infrastructure.dao.*;
import com.afiona.center.web.promotion.infrastructure.model.*;
import org.springframework.stereotype.Service;

import com.afiona.center.client.promotion.domain.aggregate.PromotionAggregate;
import com.afiona.center.client.promotion.domain.model.promotionrule.suit.MutualExclusionPromotion;
import com.afiona.center.client.promotion.domain.model.suitrule.SuitChannel;
import com.afiona.center.client.promotion.domain.model.suitrule.SuitGoods;
import com.afiona.center.client.promotion.domain.model.suitrule.SuitMember;
import com.afiona.center.client.promotion.domain.model.suitrule.SuitRule;
import com.afiona.common.util.CloneUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.hutool.core.collection.CollectionUtil;

/**
 * 基础促销活动聚合器
 *
 * @author dengweiyi
 * @date 2020-02-10
 */
@Service
public class BaseAggregateService<T extends PromotionAggregate> implements IBaseAggregate<T> {
    @Resource
    private SuitRuleDAO suitRuleDAO;

    @Resource
    private SuitChannelDAO suitChannelDAO;

    @Resource
    private SuitMemberDAO suitMemberDAO;

    @Resource
    private SuitGoodsDAO suitGoodsDAO;

    @Resource
    private ActivityDAO activityDAO;

    @Resource
    private EquitiesDAO equitiesDAO;

    @Resource
    private MutualExclusionPromotionDAO mutualExclusionPromotionDAO;

    @Override
    public void aggregate(List<T> promotionAggregates){
        if(com.deepexi.util.CollectionUtil.isEmpty(promotionAggregates)){
            return;
        }
        List<Long> promotionIds = promotionAggregates.stream().map(PromotionAggregate::getId).collect(Collectors.toList());
        List<SuitRuleDO> suitRuleDOS = suitRuleDAO.list(new QueryWrapper<SuitRuleDO>()
                .in("promotion_id", promotionIds));
        List<SuitRule> suitRules = CloneUtil.cloneList(suitRuleDOS, SuitRule.class);
        Map<Long, SuitRule> suitRuleMap = suitRules.stream().collect(Collectors.toMap(SuitRule::getPromotionId, Function.identity()));

        //权益
        List<EquitiesDO> doList = equitiesDAO.list(new LambdaQueryWrapper<EquitiesDO>().in(EquitiesDO::getPromotionId, promotionIds));
        List<Equities> equitiesList = CloneUtil.cloneList(doList, Equities.class);
        Map<Long, Equities> equitiesMap = equitiesList.stream().collect(Collectors.toMap(Equities::getPromotionId, Function.identity()));

        //互斥促销
        List<MutualExclusionPromotionDO> dos = mutualExclusionPromotionDAO.list(new LambdaQueryWrapper<MutualExclusionPromotionDO>().in(MutualExclusionPromotionDO::getPromotionId, promotionIds));
        List<MutualExclusionPromotion> mutualExclusionPromotions = CloneUtil.cloneList(dos, MutualExclusionPromotion.class);
        Map<Long, List<MutualExclusionPromotion>> mutualExclusionPromotionMap = mutualExclusionPromotions.stream().collect(Collectors.groupingBy(MutualExclusionPromotion::getPromotionId,Collectors.toList()));
        Map<Long, PromotionAggregate> promotionAggregateMap = promotionAggregates.stream()
                .collect(Collectors.toMap(PromotionAggregate::getId, Function.identity()));
        for(Map.Entry<Long, PromotionAggregate> entry : promotionAggregateMap.entrySet()){
            SuitRule suitRule = suitRuleMap.get(entry.getKey());
            if(suitRule != null){
                entry.getValue().setSuitRule(suitRule);
            }
            Equities equities = equitiesMap.get(entry.getKey());
            if(equities==null){
                continue;
            }
            List<MutualExclusionPromotion> mutualExclusionPromotionList = mutualExclusionPromotionMap.get(entry.getKey());
            if(CollectionUtil.isNotEmpty(mutualExclusionPromotionList)){
                equities.setMutualExclusionPromotions(mutualExclusionPromotionList);
            }
            entry.getValue().setEquities(equities);

        }
        aggregateActivity(promotionAggregates);

        aggregateteSuitRules(suitRuleMap, promotionIds);

    }


    public void aggregateActivity(List<? extends Promotion> promotions) {
        //聚合活动
        Set<Long> activityIds = promotions.stream().map(Promotion::getActivityId).filter(Objects::nonNull).collect(Collectors.toSet());
        if(CollectionUtil.isEmpty(activityIds)){
            return ;
        }
        Collection<ActivityDO> dos = activityDAO.listByIds(activityIds);
        if(CollectionUtil.isEmpty(dos)){
            return ;
        }
        Map<Long, ActivityDO> doMap = dos.stream().collect(Collectors.toMap(ActivityDO::getId, Function.identity()));
        promotions.stream().filter(Objects::nonNull).forEach(promotion->setActivityName(promotion,doMap));
    }

    private void setActivityName(Promotion promotion,Map<Long, ActivityDO> doMap) {
        if(promotion==null){
           return;
        }
        Long activityId = promotion.getActivityId();
        if(activityId==null||activityId==0){
           return;
        }
        ActivityDO activityDO = doMap.get(activityId);
        if(activityDO==null){
            return;
        }
        promotion.setActivityName(activityDO.getName());
    }

    private void aggregateteSuitRules(Map<Long, SuitRule> suitRuleMap, List<Long> promotionIds){
        // 适用渠道
        List<SuitChannelDO> suitChannelDOS = suitChannelDAO.list(new QueryWrapper<SuitChannelDO>()
                .in("promotion_id", promotionIds));
        List<SuitChannel> allSuitChannels = CloneUtil.cloneList(suitChannelDOS, SuitChannel.class);
        Map<Long, List<SuitChannel>> suitChannelMap = allSuitChannels.stream().collect(Collectors.groupingBy(SuitChannel::getPromotionId));

        // 适用对象
        List<SuitMemberDO> suitMemberDOS = suitMemberDAO.list(new QueryWrapper<SuitMemberDO>()
                .in("promotion_id", promotionIds));
        List<SuitMember> allSuitMembers = CloneUtil.cloneList(suitMemberDOS, SuitMember.class);
        Map<Long, List<SuitMember>> suitMemberMap = allSuitMembers.stream().collect(Collectors.groupingBy(SuitMember::getPromotionId));

        // 适用商品
        List<SuitGoodsDO> suitGoodsDOS = suitGoodsDAO.list(new QueryWrapper<SuitGoodsDO>()
                .in("promotion_id", promotionIds));
        List<SuitGoods> allSuitGoods = CloneUtil.cloneList(suitGoodsDOS, SuitGoods.class);
        Map<Long, List<SuitGoods>> suitGoodsMap = allSuitGoods.stream().collect(Collectors.groupingBy(SuitGoods::getPromotionId));

        for(Map.Entry<Long, SuitRule> entry : suitRuleMap.entrySet()){
            Long promotionId = entry.getKey();
            SuitRule suitRule = entry.getValue();

            List<SuitChannel> suitChannels = suitChannelMap.get(promotionId);
            if(CollectionUtil.isNotEmpty(suitChannels)){
                suitRule.setSuitChannels(suitChannels);
            }

            List<SuitMember> suitMembers = suitMemberMap.get(promotionId);
            if(CollectionUtil.isNotEmpty(suitMembers)){
                suitRule.setSuitMembers(suitMembers);
            }

            List<SuitGoods> suitGoods = suitGoodsMap.get(promotionId);
            if(CollectionUtil.isNotEmpty(suitGoods)){
                suitRule.setSuitGoods(suitGoods);
            }
        }
    }
}
