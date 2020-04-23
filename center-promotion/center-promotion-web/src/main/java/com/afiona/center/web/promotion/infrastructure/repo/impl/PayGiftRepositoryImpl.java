package com.afiona.center.web.promotion.infrastructure.repo.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.afiona.center.client.promotion.constants.PromotionResultEnum;
import com.afiona.center.client.promotion.constants.PromotionType;
import com.afiona.center.client.promotion.domain.aggregate.PayGiftAggregate;
import com.afiona.center.client.promotion.model.query.PromotionQuery;
import com.afiona.center.web.promotion.domain.repo.PayGiftRepository;
import com.afiona.center.web.promotion.domain.service.aggregate.IAggregate;
import com.afiona.center.web.promotion.domain.service.aggregate.impl.PayGiftAggregateService;
import com.afiona.center.web.promotion.domain.service.resolve.IBaseResolve;
import com.afiona.center.web.promotion.domain.service.resolve.IResolve;
import com.afiona.center.web.promotion.domain.service.resolve.impl.BaseResolveService;
import com.afiona.center.web.promotion.domain.service.resolve.impl.PayGiftResolveService;
import com.afiona.center.web.promotion.infrastructure.dao.*;
import com.afiona.center.web.promotion.infrastructure.model.*;
import com.afiona.center.client.promotion.util.PageBeanUtils;
import com.afiona.common.pojo.JsonResult;
import com.afiona.common.util.CloneUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.deepexi.util.pageHelper.PageBean;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 支付有礼repo实现
 *
 * @author LiJinXing
 * @date 2020/2/25
 */
@Repository
@Transactional(rollbackFor =Throwable.class)
public class PayGiftRepositoryImpl implements PayGiftRepository {

    @Resource
    private PromotionDAO promotionDAO;

    @Resource
    private IAggregate<PayGiftAggregate> iAggregate;

    @Resource
    private IResolve<PayGiftAggregate> iResolve;

    @Resource
    private PayGiftRuleDAO payGiftRuleDAO;

    @Resource
    private PayGiftStairRuleDAO payGiftStairRuleDAO;

    @Resource
    private PayGiftGoodsDAO payGiftGoodsDAO;

    @Resource
    private PayGiftCouponDAO payGiftCouponDAO;
    @Resource
    private IBaseResolve<PayGiftAggregate> iBaseResolve;

    @Override
    public PayGiftAggregate get(long promotionId) {
        PromotionDO promotionDO = promotionDAO.getById(promotionId);
        if(promotionDO==null){
            return null;
        }
        ArrayList<PromotionDO> promotionDOList = Lists.newArrayList(promotionDO);
        List<PayGiftAggregate> payGiftAggregates = CloneUtil.cloneList(promotionDOList, PayGiftAggregate.class);
        iAggregate.aggregate(payGiftAggregates);
        return payGiftAggregates.get(0);
    }
    @Override
    public PageBean<PayGiftAggregate> list(PromotionQuery query) {
        query.setPromotionTypes(Lists.newArrayList(PromotionType.PAY_GIFT));
        PageBean<PromotionDO> pageBean = promotionDAO.listPage(query);
        List<PayGiftAggregate> payGiftAggregates = CloneUtil.cloneList(pageBean.getContent(), PayGiftAggregate.class);
        iAggregate.aggregate(payGiftAggregates);
        return PageBeanUtils.pageBeanCopy(payGiftAggregates,pageBean);
    }

    @Override
    public List<PayGiftAggregate> listAll(PromotionQuery query) {
        query.setPromotionTypes(Lists.newArrayList(PromotionType.PAY_GIFT));
        List<PromotionDO> list = promotionDAO.listAll(query);
        List<PayGiftAggregate> payGiftAggregates = CloneUtil.cloneList(list, PayGiftAggregate.class);
        iAggregate.aggregate(payGiftAggregates);
        return payGiftAggregates;
    }

    @Override
    public JsonResult store(PayGiftAggregate payGiftAggregate) {
        payGiftAggregate.setType(PromotionType.PAY_GIFT);
        iResolve.resolve(payGiftAggregate);
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
        payGiftRuleDAO.remove(new LambdaQueryWrapper<PayGiftRuleDO>().in(PayGiftRuleDO::getPromotionId,promotionIds));
        payGiftStairRuleDAO.remove(new LambdaQueryWrapper<PayGiftStairRuleDO>().in(PayGiftStairRuleDO::getPromotionId,promotionIds));
        payGiftGoodsDAO.remove(new LambdaQueryWrapper<PayGiftGoodsDO>().in(PayGiftGoodsDO::getPromotionId,promotionIds));
        payGiftCouponDAO.remove(new LambdaQueryWrapper<PayGiftCouponDO>().in(PayGiftCouponDO::getPromotionId,promotionIds));
        iBaseResolve.removeBatchPromotion(promotionIds);
        return JsonResult.create();
    }

    @Override
    public JsonResult removePayGiftStairRuleIds(List<Long> payGiftStairRuleIds) {
        if(CollectionUtil.isEmpty(payGiftStairRuleIds)){
            JsonResult.create(PromotionResultEnum.NOT_EXIST);
        }
        payGiftCouponDAO.remove(new LambdaQueryWrapper<PayGiftCouponDO>().in(PayGiftCouponDO::getStairRuleId,payGiftStairRuleIds));
        payGiftGoodsDAO.remove(new LambdaQueryWrapper<PayGiftGoodsDO>().in(PayGiftGoodsDO::getStairRuleId,payGiftStairRuleIds));
        payGiftStairRuleDAO.removeByIds(payGiftStairRuleIds);
        return JsonResult.create();
    }
}
