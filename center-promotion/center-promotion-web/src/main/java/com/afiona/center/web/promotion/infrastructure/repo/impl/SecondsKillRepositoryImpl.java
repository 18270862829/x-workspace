package com.afiona.center.web.promotion.infrastructure.repo.impl;

import com.afiona.center.client.promotion.constants.PromotionResultEnum;
import com.afiona.center.client.promotion.constants.PromotionType;
import com.afiona.center.client.promotion.domain.aggregate.SecondsKillAggregate;
import com.afiona.center.client.promotion.model.query.PromotionQuery;
import com.afiona.center.client.promotion.util.PageBeanUtils;
import com.afiona.center.web.promotion.domain.repo.SecondsKillRepository;
import com.afiona.center.web.promotion.domain.service.aggregate.IAggregate;
import com.afiona.center.web.promotion.domain.service.aggregate.impl.SecondsKillAggregateService;
import com.afiona.center.web.promotion.domain.service.resolve.IBaseResolve;
import com.afiona.center.web.promotion.domain.service.resolve.IResolve;
import com.afiona.center.web.promotion.domain.service.resolve.impl.BaseResolveService;
import com.afiona.center.web.promotion.domain.service.resolve.impl.SecondsKillResolveService;
import com.afiona.center.web.promotion.infrastructure.dao.PromotionDAO;
import com.afiona.center.web.promotion.infrastructure.dao.SecondsKillGoodsDAO;
import com.afiona.center.web.promotion.infrastructure.dao.SecondsKillRuleDAO;
import com.afiona.center.web.promotion.infrastructure.model.PromotionDO;
import com.afiona.center.web.promotion.infrastructure.model.SecondsKillGoodsDO;
import com.afiona.center.web.promotion.infrastructure.model.SecondsKillRuleDO;
import com.afiona.common.pojo.JsonResult;
import com.afiona.common.util.CloneUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.deepexi.util.pageHelper.PageBean;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 秒杀repo实现
 *
 * @author LiJinXing
 * @date 2020/4/7
 */
@Repository
public class SecondsKillRepositoryImpl implements SecondsKillRepository {

    @Resource
    private PromotionDAO promotionDAO;

    @Resource
    private IAggregate<SecondsKillAggregate> iAggregate;

    @Resource
    private IResolve<SecondsKillAggregate> iResolve;

    @Resource
    private IBaseResolve<SecondsKillAggregate> iBaseResolve;

    @Resource
    private SecondsKillRuleDAO secondsKillRuleDAO;

    @Resource
    private SecondsKillGoodsDAO secondsKillGoodsDAO;

    @Override
    public SecondsKillAggregate get(long promotionId) {
        PromotionDO promotionDO = promotionDAO.getById(promotionId);
        if(promotionDO==null){
            return null;
        }
        ArrayList<PromotionDO> promotionDOList = Lists.newArrayList(promotionDO);
        List<SecondsKillAggregate> secondsKillAggregates = CloneUtil.cloneList(promotionDOList, SecondsKillAggregate.class);
        iAggregate.aggregate(secondsKillAggregates);
        return secondsKillAggregates.get(0);
    }

    @Override
    public PageBean<SecondsKillAggregate> list(PromotionQuery query) {
        query.setPromotionTypes(Lists.newArrayList(PromotionType.SECONDS_KILL));
        PageBean<PromotionDO> pageBean = promotionDAO.listPage(query);
        List<SecondsKillAggregate> secondsKillAggregates = CloneUtil.cloneList(pageBean.getContent(), SecondsKillAggregate.class);
        iAggregate.aggregate(secondsKillAggregates);
        return PageBeanUtils.pageBeanCopy(secondsKillAggregates,pageBean);
    }

    @Override
    public List<SecondsKillAggregate> listAll(PromotionQuery query) {
        query.setPromotionTypes(Lists.newArrayList(PromotionType.SECONDS_KILL));
        List<PromotionDO> list = promotionDAO.listAll(query);
        List<SecondsKillAggregate> secondsKillAggregates = CloneUtil.cloneList(list, SecondsKillAggregate.class);
        iAggregate.aggregate(secondsKillAggregates);
        return secondsKillAggregates;
    }

    @Override
    public JsonResult store(SecondsKillAggregate secondsKillAggregate) {
        secondsKillAggregate.setType(PromotionType.SECONDS_KILL);
        iResolve.resolve(secondsKillAggregate);
        return JsonResult.create();
    }

    @Override
    public JsonResult remove(Long id) {
        return removeBatch(Lists.newArrayList(id));
    }

    @Override
    public JsonResult removeBatch(List<Long> promotionIds) {
        if(promotionIds.isEmpty()){
            JsonResult.create(PromotionResultEnum.NOT_EXIST);
        }
        secondsKillGoodsDAO.remove(new LambdaQueryWrapper<SecondsKillGoodsDO>().in(SecondsKillGoodsDO::getPromotionId,promotionIds));
        secondsKillRuleDAO.remove(new LambdaQueryWrapper<SecondsKillRuleDO>().in(SecondsKillRuleDO::getPromotionId,promotionIds));
        iBaseResolve.removeBatchPromotion(promotionIds);
        return JsonResult.create();
    }
}
