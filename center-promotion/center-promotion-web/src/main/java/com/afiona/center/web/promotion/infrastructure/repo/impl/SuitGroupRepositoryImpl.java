package com.afiona.center.web.promotion.infrastructure.repo.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.afiona.center.client.promotion.constants.PromotionResultEnum;
import com.afiona.center.client.promotion.constants.PromotionType;
import com.afiona.center.client.promotion.domain.aggregate.SuitGroupAggregate;
import com.afiona.center.client.promotion.model.query.PromotionQuery;
import com.afiona.center.web.promotion.domain.repo.SuitGroupRepository;
import com.afiona.center.web.promotion.domain.service.aggregate.IAggregate;
import com.afiona.center.web.promotion.domain.service.aggregate.impl.SuitGroupAggregateService;
import com.afiona.center.web.promotion.domain.service.resolve.IBaseResolve;
import com.afiona.center.web.promotion.domain.service.resolve.IResolve;
import com.afiona.center.web.promotion.domain.service.resolve.impl.BaseResolveService;
import com.afiona.center.web.promotion.domain.service.resolve.impl.SuitGroupResolveService;
import com.afiona.center.web.promotion.infrastructure.dao.PromotionDAO;
import com.afiona.center.web.promotion.infrastructure.dao.SuitFixedSuitGoodsDAO;
import com.afiona.center.web.promotion.infrastructure.dao.SuitFixedSuitRuleDAO;
import com.afiona.center.web.promotion.infrastructure.dao.SuitGroupRuleDAO;
import com.afiona.center.web.promotion.infrastructure.model.PromotionDO;
import com.afiona.center.web.promotion.infrastructure.model.SuitFixedSuitGoodsDO;
import com.afiona.center.web.promotion.infrastructure.model.SuitFixedSuitRuleDO;
import com.afiona.center.web.promotion.infrastructure.model.SuitGroupRuleDO;
import com.afiona.center.client.promotion.util.PageBeanUtils;
import com.afiona.common.pojo.JsonResult;
import com.afiona.common.util.CloneUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.deepexi.util.pageHelper.PageBean;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 *组合套装repo实现
 *
 * @author LiJinXing
 * @date 2020/2/19
 */
@Repository
@Transactional(rollbackFor =Throwable.class)
public class SuitGroupRepositoryImpl implements SuitGroupRepository {

    @Resource
    private PromotionDAO promotionDAO;

    @Resource
    private IAggregate<SuitGroupAggregate> iAggregate;

    @Resource
    private IResolve<SuitGroupAggregate> iResolve;

    @Resource
    private IBaseResolve<SuitGroupAggregate> iBaseResolve;

    @Resource
    private SuitGroupRuleDAO suitGroupRuleDAO;

    @Resource
    private SuitFixedSuitGoodsDAO suitFixedSuitGoodsDAO;

    @Resource
    private SuitFixedSuitRuleDAO suitFixedSuitRuleDAO;

    @Override
    public SuitGroupAggregate get(long promotionId) {
        PromotionDO promotionDO = promotionDAO.getById(promotionId);
        if(promotionDO==null){
            return null;
        }
        List<SuitGroupAggregate> suitGroupAggregates = CloneUtil.cloneList(Lists.newArrayList(promotionDO), SuitGroupAggregate.class);
        iAggregate.aggregate(suitGroupAggregates);
        return suitGroupAggregates.get(0);
    }

    @Override
    public PageBean<SuitGroupAggregate> list(PromotionQuery query) {
        query.setPromotionTypes(Lists.newArrayList(PromotionType.SUIT_GROUP));
        PageBean<PromotionDO> pageBean = promotionDAO.listPage(query);
        List<SuitGroupAggregate> suitGroupAggregates = CloneUtil.cloneList(pageBean.getContent(), SuitGroupAggregate.class);
        iAggregate.aggregate(suitGroupAggregates);
        return PageBeanUtils.pageBeanCopy(suitGroupAggregates,pageBean);
    }

    @Override
    public List<SuitGroupAggregate> listAll(PromotionQuery query) {
        query.setPromotionTypes(Lists.newArrayList(PromotionType.SUIT_GROUP));
        List<PromotionDO> list = promotionDAO.listAll(query);
        List<SuitGroupAggregate> suitGroupAggregates = CloneUtil.cloneList(list, SuitGroupAggregate.class);
        iAggregate.aggregate(suitGroupAggregates);
        return suitGroupAggregates;
    }

    @Override
    public JsonResult store(SuitGroupAggregate suitGroupAggregate) {
        suitGroupAggregate.setType(PromotionType.SUIT_GROUP);
        iResolve.resolve(suitGroupAggregate);
        return JsonResult.create();
    }

    @Override
    public JsonResult remove(Long id) {
        return removeBatch(Lists.newArrayList(id));
    }

    @Override
    public JsonResult removeBatch(List<Long> promotionIds) {
        if(CollectionUtil.isEmpty(promotionIds)){
            JsonResult.create(PromotionResultEnum.NOT_EXIST);
        }
        suitGroupRuleDAO.remove(new LambdaQueryWrapper<SuitGroupRuleDO>().in(SuitGroupRuleDO::getPromotionId,promotionIds));
        suitFixedSuitGoodsDAO.remove(new LambdaQueryWrapper<SuitFixedSuitGoodsDO>().in(SuitFixedSuitGoodsDO::getPromotionId,promotionIds));
        suitFixedSuitRuleDAO.remove(new LambdaQueryWrapper<SuitFixedSuitRuleDO>().in(SuitFixedSuitRuleDO::getPromotionId,promotionIds));
        iBaseResolve.removeBatchPromotion(promotionIds);
        return JsonResult.create();
    }

    @Override
    public JsonResult removeSuitFixedSuitRule(Long suitFixedSuitRuleId) {
        suitFixedSuitGoodsDAO.remove(new LambdaQueryWrapper<SuitFixedSuitGoodsDO>().eq(SuitFixedSuitGoodsDO::getSuitFixedSuitId,suitFixedSuitRuleId));
        suitFixedSuitRuleDAO.removeById(suitFixedSuitRuleId);
        return JsonResult.create();
    }

    @Override
    public JsonResult removeSuitFixedSuitGoods(Long suitFixedSuitGoodsId) {
        suitFixedSuitGoodsDAO.removeById(suitFixedSuitGoodsId);
        return JsonResult.create();
    }
}
