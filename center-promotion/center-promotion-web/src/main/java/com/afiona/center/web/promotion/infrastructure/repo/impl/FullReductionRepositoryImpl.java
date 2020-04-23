package com.afiona.center.web.promotion.infrastructure.repo.impl;

import java.util.List;

import javax.annotation.Resource;

import com.afiona.center.client.promotion.constants.PromotionType;
import com.afiona.center.client.promotion.constants.StatusType;
import com.afiona.center.web.promotion.domain.service.aggregate.IAggregate;
import com.afiona.center.web.promotion.domain.service.resolve.IBaseResolve;
import com.afiona.center.web.promotion.domain.service.resolve.IResolve;
import com.afiona.center.web.promotion.infrastructure.dao.*;
import com.afiona.center.web.promotion.infrastructure.model.*;
import com.afiona.center.client.promotion.util.PageBeanUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.deepexi.util.pageHelper.PageBean;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.afiona.center.client.promotion.constants.PromotionResultEnum;
import com.afiona.center.client.promotion.domain.aggregate.FullReductionAggregate;
import com.afiona.center.client.promotion.model.query.PromotionQuery;
import com.afiona.center.web.promotion.domain.repo.FullReductionRepository;
import com.afiona.center.web.promotion.domain.service.aggregate.impl.FullReductionAggregateService;
import com.afiona.center.web.promotion.domain.service.resolve.impl.BaseResolveService;
import com.afiona.center.web.promotion.domain.service.resolve.impl.FullReductionResolveService;
import com.afiona.common.pojo.JsonResult;
import com.afiona.common.util.CloneUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * 满减repo实现
 *
 * @author dengweiyi
 * @date 2020-02-09
 */
@Repository
@Transactional(rollbackFor =Throwable.class)
public class FullReductionRepositoryImpl implements FullReductionRepository {

    @Resource
    private PromotionDAO promotionDAO;

    @Resource
    private IBaseResolve<FullReductionAggregate> iBaseResolve;

    @Resource
    private IAggregate<FullReductionAggregate> iAggregate;

    @Resource
    private IResolve<FullReductionAggregate> iResolve;

    @Resource
    private FullReductionRuleDAO fullReductionRuleDAO;

    @Resource
    private FullReductionStairRuleDAO fullReductionStairRuleDAO;

    @Resource
    private FullReductionCouponDAO fullReductionCouponDAO;

    @Resource
    private FullReductionGitGoodsDAO fullReductionGitGoodsDAO;

    @Override
    public FullReductionAggregate get(long promotionId) {
        PromotionDO promotionDO = promotionDAO.getById(promotionId);
        if(promotionDO==null){
            return null;
        }
        List<FullReductionAggregate> fullReductionAggregates = CloneUtil.cloneList(Lists.newArrayList(promotionDO), FullReductionAggregate.class);
        iAggregate.aggregate(fullReductionAggregates);
        return fullReductionAggregates.get(0);
    }

    @Override
    public PageBean<FullReductionAggregate> list(PromotionQuery query) {
        query.setPromotionTypes(Lists.newArrayList(PromotionType.FULL_REDUCTION));
        PageBean<PromotionDO> pageBean = promotionDAO.listPage(query);
        List<FullReductionAggregate> fullReductionAggregates = CloneUtil.cloneList(pageBean.getContent(), FullReductionAggregate.class);
        iAggregate.aggregate(fullReductionAggregates);
        return PageBeanUtils.pageBeanCopy(fullReductionAggregates,pageBean);
    }

    @Override
    public List<FullReductionAggregate> listAll(PromotionQuery query) {
        query.setPromotionTypes(Lists.newArrayList(PromotionType.FULL_REDUCTION));
        List<PromotionDO> list = promotionDAO.listAll(query);
        List<FullReductionAggregate> fullReductionAggregates = CloneUtil.cloneList(list, FullReductionAggregate.class);
        iAggregate.aggregate(fullReductionAggregates);
        return fullReductionAggregates;
    }

    @Override
    public JsonResult store(FullReductionAggregate fullReductionAggregate) {
        fullReductionAggregate.setType(PromotionType.FULL_REDUCTION);
        fullReductionAggregate.setStatus(StatusType.HAS_NOT_STARTED);
        iResolve.resolve(fullReductionAggregate);
        return JsonResult.create();
    }

    @Override
    public JsonResult remove(Long id) {
        return removeBatch(Lists.newArrayList(id));
    }

    @Override
    public JsonResult removeBatch(List<Long> ids) {
        if(ids.isEmpty()){
            JsonResult.create(PromotionResultEnum.NOT_EXIST);
        }
        iBaseResolve.removeBatchPromotion(ids);
        fullReductionRuleDAO.remove(new QueryWrapper<FullReductionRuleDO>().in("promotion_id",ids));
        fullReductionStairRuleDAO.remove(new QueryWrapper<FullReductionStairRuleDO>().in("promotion_id",ids));
        fullReductionCouponDAO.remove(new QueryWrapper<FullReductionCouponDO>().in("promotion_id",ids));
        fullReductionGitGoodsDAO.remove(new QueryWrapper<FullReductionGitGoodsDO>().in("promotion_id",ids));
        return JsonResult.create();
    }


    @Override
    public JsonResult removeFullReductionStairRule(Long stairRuleId) {

        if(stairRuleId==null||stairRuleId==0){
            return JsonResult.create(4000,"传递参数为null");
        }
       fullReductionStairRuleDAO.remove(new LambdaQueryWrapper<FullReductionStairRuleDO>().eq(FullReductionStairRuleDO::getId,stairRuleId));
       fullReductionCouponDAO.remove(new LambdaQueryWrapper<FullReductionCouponDO>().eq(FullReductionCouponDO::getStairRuleId,stairRuleId));
       fullReductionGitGoodsDAO.remove(new LambdaQueryWrapper<FullReductionGitGoodsDO>().eq(FullReductionGitGoodsDO::getStairRuleId,stairRuleId));
       return JsonResult.create();
    }
}
