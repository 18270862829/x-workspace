package com.afiona.center.web.promotion.domain.service.resolve.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.afiona.center.client.promotion.constants.PromotionType;
import com.afiona.center.client.promotion.constants.StatusType;
import com.afiona.center.client.promotion.domain.model.Promotion;
import com.afiona.center.client.promotion.domain.model.equities.Equities;
import com.afiona.center.client.promotion.domain.model.promotionrule.suit.MutualExclusionPromotion;
import com.afiona.center.client.promotion.util.Encoder;
import com.afiona.center.web.promotion.domain.service.resolve.IBaseResolve;
import com.afiona.center.web.promotion.domain.service.resolve.IResolve;
import com.afiona.center.web.promotion.infrastructure.dao.*;
import com.afiona.center.web.promotion.infrastructure.model.*;
import com.afiona.common.util.BizException;
import com.deepexi.util.CollectionUtil;
import org.springframework.stereotype.Service;

import com.afiona.center.client.promotion.constants.PromotionResultEnum;
import com.afiona.center.client.promotion.domain.aggregate.PromotionAggregate;
import com.afiona.center.client.promotion.domain.model.suitrule.SuitRule;
import com.afiona.common.pojo.JsonResult;
import com.afiona.common.util.CloneUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deepexi.util.pojo.CloneDirection;
import com.deepexi.util.pojo.ObjectCloneUtils;

import cn.hutool.core.util.ObjectUtil;

/**
 * 基础活动分解
 *
 * @author LiJinXing
 * @date 2020/2/12
 */
@Service
public class BaseResolveService<T extends PromotionAggregate> implements IBaseResolve<T> {

    @Resource
    private PromotionDAO promotionDAO;

    @Resource
    private SuitRuleDAO suitRuleDAO;

    @Resource
    private SuitChannelDAO suitChannelDAO;

    @Resource
    private SuitMemberDAO suitMemberDAO;

    @Resource
    private SuitGoodsDAO suitGoodsDAO;

    @Resource
    private EquitiesDAO equitiesDAO;

    @Resource
    private ActivityDAO activityDAO;

    @Resource
    private MutualExclusionPromotionDAO mutualExclusionPromotionDAO;

    @Override
    public Long resolve(T promotionAggregate){
        priorityCalc(promotionAggregate);
        //保存活动基础信息
        PromotionDO promotionDO = CloneUtil.clone(promotionAggregate, PromotionDO.class);
        if(ObjectUtil.isNull(promotionDO)){
            return null;
        }
        //活动时间校验
        activityTimeVerify(promotionDO);
        promotionDO.setStatus(promotionAggregate.getStatus());
        //设置编码
        promotionDO.setEncoding(Encoder.coding());
        promotionDAO.saveOrUpdate(promotionDO);
        //保存规则信息
        resolveSuitRules(promotionAggregate,promotionDO.getId());
        //保存权益
        resolveEquities(promotionAggregate,promotionDO.getId());
        return promotionDO.getId();
    }

    private void activityTimeVerify(PromotionDO promotionDO) {
        if(promotionDO.getType().equals(PromotionType.COUPON)){
            return;
        }
        Long activityId = promotionDO.getActivityId();
        if(activityId==null){
           return;
        }
        ActivityDO activityDO = activityDAO.getById(activityId);
        if(activityDO==null){
           throw new BizException("所选活动不存在");
        }
        if(activityDO.getStatus().equals(StatusType.EXPIRED)){
            throw new BizException("活动已结束,不能该活动下面创建促销");
        }
        Date startTime = promotionDO.getStartTime();
        Date endTime = promotionDO.getEndTime();
        if(endTime==null||startTime==null){
            throw new BizException(startTime==null?"startTime不能为null":"endTime不能为null");
        }
        StringBuilder builder =new StringBuilder();
        if(promotionDO.getStartTime().before(activityDO.getStartTime())){
            builder.append("促销开始时间不能在活动开始时间之前");
        }
        if(promotionDO.getEndTime().after(activityDO.getEndTime())){
            builder.append("促销结束时间不能在活动结束时间之后");
        }
        if(promotionDO.getStartTime().after(promotionDO.getEndTime())){
            builder.append("促销的开始时间不能再结束时间之后");
        }
        if(builder.length()==0){
           return;
        }
        throw new BizException(builder.toString());
    }

    private void resolveEquities(PromotionAggregate promotionAggregate, Long id) {
        Equities equities = promotionAggregate.getEquities();
        if(equities==null){
            return;
        }
        EquitiesDO aDo = CloneUtil.clone(equities, EquitiesDO.class).setPromotionId(id);
        equitiesDAO.saveOrUpdate(aDo);

        List<MutualExclusionPromotion> mutualExclusionPromotions = equities.getMutualExclusionPromotions();
        if(CollectionUtil.isEmpty(mutualExclusionPromotions)){
            return;
        }
        mutualExclusionPromotions.forEach(mutualExclusionPromotion -> mutualExclusionPromotion.setPromotionId(id));
        List<MutualExclusionPromotionDO> dos = CloneUtil.cloneList(mutualExclusionPromotions, MutualExclusionPromotionDO.class);
        mutualExclusionPromotionDAO.saveOrUpdateBatch(dos);
    }

    private void resolveSuitRules(PromotionAggregate promotionAggregate,Long promotionId){
        if(promotionId==null){
            JsonResult.create(PromotionResultEnum.DATA_ERROR);
        }
        SuitRule suitRule = promotionAggregate.getSuitRule();
        //保存适用规则
        SuitRuleDO suitRuleDO = CloneUtil.clone(suitRule, SuitRuleDO.class);
        suitRuleDO.setPromotionId(promotionId);
        suitRuleDAO.saveOrUpdate(suitRuleDO);

        //保存适用渠道
        List<SuitChannelDO> suitChannelDOS= CloneUtil.cloneList(suitRule.getSuitChannels(), SuitChannelDO.class);
        if(suitChannelDOS!=null&&!suitChannelDOS.isEmpty()){
            suitChannelDOS.forEach(SuitChannelDO -> SuitChannelDO.setPromotionId(promotionId));
            suitChannelDAO.saveOrUpdateBatch(suitChannelDOS);
        }

        //保存适用对象
        List<SuitMemberDO> suitMemberDOS = CloneUtil.cloneList(suitRule.getSuitMembers(), SuitMemberDO.class);
        if(suitMemberDOS!=null&&!suitMemberDOS.isEmpty()){
            suitMemberDOS.forEach(suitMemberDO -> suitMemberDO.setPromotionId(promotionId));
            suitMemberDAO.saveOrUpdateBatch(suitMemberDOS);
        }

        //保存适用商品
        List<SuitGoodsDO> suitGoodsDOS = CloneUtil.cloneList(suitRule.getSuitGoods(), SuitGoodsDO.class);
        if(suitGoodsDOS!=null&&!suitGoodsDOS.isEmpty()){
            suitGoodsDOS.forEach(suitGoodsDO -> suitGoodsDO.setPromotionId(promotionId));
            suitGoodsDAO.saveOrUpdateBatch(suitGoodsDOS);
        }
    }

    @Override
    public void removeBatchPromotion(List<Long> promotionIds){
        promotionDAO.removeByIds(promotionIds);
        suitRuleDAO.remove(new QueryWrapper<SuitRuleDO>().in("promotion_id",promotionIds));
        suitChannelDAO.remove(new QueryWrapper<SuitChannelDO>().in("promotion_id",promotionIds));
        suitMemberDAO.remove(new QueryWrapper<SuitMemberDO>().in("promotion_id",promotionIds));
        suitGoodsDAO.remove(new QueryWrapper<SuitGoodsDO>().in("promotion_id",promotionIds));
        equitiesDAO.remove(new LambdaQueryWrapper<EquitiesDO>().in(EquitiesDO::getPromotionId,promotionIds));
        mutualExclusionPromotionDAO.remove(new LambdaQueryWrapper<MutualExclusionPromotionDO>().in(MutualExclusionPromotionDO::getPromotionId,promotionIds));
    }

    private void priorityCalc(Promotion promotion){
        Long priority = promotion.getType().getPriority();
        promotion.setPriority(priority);
    }
}
