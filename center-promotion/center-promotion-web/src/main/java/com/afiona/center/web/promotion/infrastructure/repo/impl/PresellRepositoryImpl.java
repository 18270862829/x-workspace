package com.afiona.center.web.promotion.infrastructure.repo.impl;

import com.afiona.center.client.promotion.constants.PromotionType;
import com.afiona.center.client.promotion.domain.aggregate.PresellAggregate;
import com.afiona.center.client.promotion.model.query.PromotionQuery;
import com.afiona.center.client.promotion.util.PageBeanUtils;
import com.afiona.center.web.promotion.domain.repo.PresellRepository;
import com.afiona.center.web.promotion.domain.service.aggregate.IAggregate;
import com.afiona.center.web.promotion.domain.service.resolve.IBaseResolve;
import com.afiona.center.web.promotion.domain.service.resolve.IResolve;
import com.afiona.center.web.promotion.infrastructure.dao.PresellGoodsDAO;
import com.afiona.center.web.promotion.infrastructure.dao.PresellRuleDAO;
import com.afiona.center.web.promotion.infrastructure.dao.PromotionDAO;
import com.afiona.center.web.promotion.infrastructure.model.PresellGoodsDO;
import com.afiona.center.web.promotion.infrastructure.model.PresellRuleDO;
import com.afiona.center.web.promotion.infrastructure.model.PromotionDO;
import com.afiona.common.pojo.JsonResult;
import com.afiona.common.util.CloneUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.deepexi.util.CollectionUtil;
import com.deepexi.util.pageHelper.PageBean;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 预售repo实现
 *
 * @author LiJinXing
 * @date 2020/4/22
 */
@Repository
public class PresellRepositoryImpl implements PresellRepository {

    @Resource
    private PromotionDAO promotionDAO;

    @Resource
    private IAggregate<PresellAggregate> iAggregate;

    @Resource
    private IResolve<PresellAggregate> iResolve;

    @Resource
    private IBaseResolve<PresellAggregate> iBaseResolve;

    @Resource
    private PresellRuleDAO presellRuleDAO;

    @Resource
    private PresellGoodsDAO presellGoodsDAO;

    @Override
    public PresellAggregate get(long promotionId) {
        PromotionDO promotionDO = promotionDAO.getById(promotionId);
        if(promotionDO==null){
            return null;
        }
        PresellAggregate aggregate = CloneUtil.clone(promotionDO, PresellAggregate.class);
        iAggregate.aggregate(Lists.newArrayList(aggregate));
        return aggregate;
    }

    @Override
    public PageBean<PresellAggregate> list(PromotionQuery query) {
        query.setPromotionTypes(Lists.newArrayList(PromotionType.PRESELL));
        PageBean<PromotionDO> pageBean = promotionDAO.listPage(query);
        List<PresellAggregate> aggregates = CloneUtil.cloneList(pageBean.getContent(), PresellAggregate.class);
        iAggregate.aggregate(aggregates);
        return PageBeanUtils.pageBeanCopy(aggregates,pageBean);
    }

    @Override
    public List<PresellAggregate> listAll(PromotionQuery query) {
        query.setPromotionTypes(Lists.newArrayList(PromotionType.PRESELL));
        List<PromotionDO> list = promotionDAO.listAll(query);
        List<PresellAggregate> aggregates = CloneUtil.cloneList(list, PresellAggregate.class);
        iAggregate.aggregate(aggregates);
        return aggregates;
    }

    @Override
    public JsonResult store(PresellAggregate presellAggregate) {
        presellAggregate.setType(PromotionType.PRESELL);
        iResolve.resolve(presellAggregate);
        return JsonResult.create();
    }

    @Override
    public JsonResult remove(Long id) {
        return removeBatch(Lists.newArrayList(id));
    }

    @Override
    public JsonResult removeBatch(List<Long> ids) {
        if(CollectionUtil.isEmpty(ids)){
            return JsonResult.create();
        }
        presellGoodsDAO.remove(new LambdaQueryWrapper<PresellGoodsDO>().in(PresellGoodsDO::getPromotionId,ids));
        presellRuleDAO.remove(new LambdaQueryWrapper<PresellRuleDO>().in(PresellRuleDO::getPromotionId,ids));
        iBaseResolve.removeBatchPromotion(ids);
        return JsonResult.create();
    }


}
