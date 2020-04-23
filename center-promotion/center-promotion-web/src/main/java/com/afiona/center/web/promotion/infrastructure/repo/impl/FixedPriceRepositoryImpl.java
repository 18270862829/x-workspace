package com.afiona.center.web.promotion.infrastructure.repo.impl;

import java.util.List;

import javax.annotation.Resource;

import com.afiona.center.client.promotion.constants.PromotionType;
import com.afiona.center.client.promotion.util.PageBeanUtils;
import com.afiona.center.web.promotion.domain.service.aggregate.IAggregate;
import com.afiona.center.web.promotion.domain.service.resolve.IBaseResolve;
import com.afiona.center.web.promotion.domain.service.resolve.IResolve;
import com.afiona.center.web.promotion.infrastructure.dao.FixedPriceGoodsDAO;
import com.afiona.center.web.promotion.infrastructure.model.FixedPriceGoodsDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.deepexi.util.pageHelper.PageBean;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.afiona.center.client.promotion.constants.PromotionResultEnum;
import com.afiona.center.client.promotion.domain.aggregate.FixedPriceAggregate;
import com.afiona.center.client.promotion.model.query.PromotionQuery;
import com.afiona.center.web.promotion.domain.repo.FixedPriceRepository;
import com.afiona.center.web.promotion.domain.service.aggregate.impl.FixedPriceAggregateService;
import com.afiona.center.web.promotion.domain.service.resolve.impl.BaseResolveService;
import com.afiona.center.web.promotion.domain.service.resolve.impl.FixedPriceResolveService;
import com.afiona.center.web.promotion.infrastructure.dao.FixedPriceRuleDAO;
import com.afiona.center.web.promotion.infrastructure.dao.PromotionDAO;
import com.afiona.center.web.promotion.infrastructure.model.FixedPriceRuleDO;
import com.afiona.center.web.promotion.infrastructure.model.PromotionDO;
import com.afiona.common.pojo.JsonResult;
import com.afiona.common.util.CloneUtil;

/**
 * 一口价repo实现
 *
 * @author LiJinXing
 * @date 2020/2/13
 */
@Repository
@Transactional(rollbackFor =Throwable.class)
public class FixedPriceRepositoryImpl implements FixedPriceRepository {

    @Resource
    private PromotionDAO promotionDAO;

    @Resource
    private IAggregate<FixedPriceAggregate> iAggregate;

    @Resource
    private IResolve<FixedPriceAggregate> iResolve;

    @Resource
    private IBaseResolve<FixedPriceAggregate> iBaseResolve;

    @Resource
    private FixedPriceRuleDAO fixedPriceRuleDAO;

    @Resource
    private FixedPriceGoodsDAO fixedPriceGoodsDAO;

    @Override
    public FixedPriceAggregate get(long promotionId) {
        PromotionDO promotionDO = promotionDAO.getById(promotionId);
        if(promotionDO==null){
            return null;
        }
        List<FixedPriceAggregate> promotions = CloneUtil.cloneList(Lists.newArrayList(promotionDO),
                FixedPriceAggregate.class);
        iAggregate.aggregate(promotions);
        return promotions.get(0);
    }

    @Override
    public PageBean<FixedPriceAggregate> list(PromotionQuery query) {
        query.setPromotionTypes(Lists.newArrayList(PromotionType.FIXED_PRICE));
        PageBean<PromotionDO> pageBean = promotionDAO.listPage(query);
        List<FixedPriceAggregate> promotions = CloneUtil.cloneList(pageBean.getContent(),
                FixedPriceAggregate.class);
        iAggregate.aggregate(promotions);
        return PageBeanUtils.pageBeanCopy(promotions,pageBean);
    }

    @Override
    public List<FixedPriceAggregate> listAll(PromotionQuery query) {
        query.setPromotionTypes(Lists.newArrayList(PromotionType.FIXED_PRICE));
        List<PromotionDO> list = promotionDAO.listAll(query);
        List<FixedPriceAggregate> promotions = CloneUtil.cloneList(list,
                FixedPriceAggregate.class);
        iAggregate.aggregate(promotions);
        return promotions;
    }

    @Override
    public JsonResult store(FixedPriceAggregate fixedPriceAggregate) {
        fixedPriceAggregate.setType(PromotionType.FIXED_PRICE);
        iResolve.resolve(fixedPriceAggregate);
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
        fixedPriceRuleDAO.remove(new LambdaQueryWrapper<FixedPriceRuleDO>().in(FixedPriceRuleDO::getPromotionId,ids));
        fixedPriceGoodsDAO.remove(new LambdaQueryWrapper<FixedPriceGoodsDO>().in(FixedPriceGoodsDO::getPromotionId,ids));
        return JsonResult.create();
    }
}
