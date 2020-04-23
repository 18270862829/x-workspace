package com.afiona.center.web.promotion.infrastructure.repo.impl;

import java.util.List;

import javax.annotation.Resource;

import com.afiona.center.client.promotion.constants.PromotionType;
import com.afiona.center.client.promotion.util.PageBeanUtils;
import com.afiona.center.web.promotion.domain.service.aggregate.IAggregate;
import com.afiona.center.web.promotion.domain.service.resolve.IBaseResolve;
import com.afiona.center.web.promotion.domain.service.resolve.IResolve;
import com.deepexi.util.pageHelper.PageBean;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.afiona.center.client.promotion.constants.PromotionResultEnum;
import com.afiona.center.client.promotion.domain.aggregate.SpecialDiscountAggregate;
import com.afiona.center.client.promotion.model.query.PromotionQuery;
import com.afiona.center.web.promotion.domain.repo.SpecialDiscountRepository;
import com.afiona.center.web.promotion.domain.service.aggregate.impl.SpecialDiscountAggregateService;
import com.afiona.center.web.promotion.domain.service.resolve.impl.BaseResolveService;
import com.afiona.center.web.promotion.domain.service.resolve.impl.SpecialDiscountResolveService;
import com.afiona.center.web.promotion.infrastructure.dao.PromotionDAO;
import com.afiona.center.web.promotion.infrastructure.dao.SpecialDiscountGoodsDAO;
import com.afiona.center.web.promotion.infrastructure.dao.SpecialDiscountRuleDAO;
import com.afiona.center.web.promotion.infrastructure.model.PromotionDO;
import com.afiona.center.web.promotion.infrastructure.model.SpecialDiscountGoodsDO;
import com.afiona.center.web.promotion.infrastructure.model.SpecialDiscountRuleDO;
import com.afiona.common.pojo.JsonResult;
import com.afiona.common.util.CloneUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * 限时折扣repo实现
 *
 * @author LiJinXing
 * @date 2020/2/18
 */
@Repository
@Transactional(rollbackFor =Throwable.class)
public class SpecialDiscountRepositoryImpl implements SpecialDiscountRepository {

    @Resource
    private PromotionDAO promotionDAO;

    @Resource
    private IAggregate<SpecialDiscountAggregate> iAggregate;

    @Resource
    private IResolve<SpecialDiscountAggregate> iResolve;

    @Resource
    private IBaseResolve<SpecialDiscountAggregate> iBaseResolve;

    @Resource
    private SpecialDiscountRuleDAO specialDiscountRuleDAO;

    @Resource
    private SpecialDiscountGoodsDAO specialDiscountGoodsDAO;

    @Override
    public SpecialDiscountAggregate get(long promotionId) {
        PromotionDO promotionDO = promotionDAO.getById(promotionId);
        if(promotionDO==null){
            return null;
        }
        List<SpecialDiscountAggregate> specialDiscountAggregates = CloneUtil.cloneList(Lists.newArrayList(promotionDO), SpecialDiscountAggregate.class);
        iAggregate.aggregate(specialDiscountAggregates);
        return specialDiscountAggregates.get(0);
    }

    @Override
    public PageBean<SpecialDiscountAggregate> list(PromotionQuery query) {
        query.setPromotionTypes(Lists.newArrayList(PromotionType.SPECIAL_DISCOUNT));
        PageBean<PromotionDO> pageBean = promotionDAO.listPage(query);
        List<SpecialDiscountAggregate> specialDiscountAggregates = CloneUtil.cloneList(pageBean.getContent(), SpecialDiscountAggregate.class);
        iAggregate.aggregate(specialDiscountAggregates);
        return PageBeanUtils.pageBeanCopy(specialDiscountAggregates,pageBean);
    }

    @Override
    public List<SpecialDiscountAggregate> listAll(PromotionQuery query) {
        query.setPromotionTypes(Lists.newArrayList(PromotionType.SPECIAL_DISCOUNT));
        List<PromotionDO> list = promotionDAO.listAll(query);
        List<SpecialDiscountAggregate> specialDiscountAggregates = CloneUtil.cloneList(list, SpecialDiscountAggregate.class);
        iAggregate.aggregate(specialDiscountAggregates);
        return specialDiscountAggregates;
    }

    @Override
    public JsonResult store(SpecialDiscountAggregate specialDiscountAggregate) {
        specialDiscountAggregate.setType(PromotionType.SPECIAL_DISCOUNT);
        iResolve.resolve(specialDiscountAggregate);
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
        specialDiscountGoodsDAO.remove(new QueryWrapper<SpecialDiscountGoodsDO>().in("promotion_id",promotionIds));
        specialDiscountRuleDAO.remove(new QueryWrapper<SpecialDiscountRuleDO>().in("promotion_id",promotionIds));
        iBaseResolve.removeBatchPromotion(promotionIds);
        return JsonResult.create();
    }
}
