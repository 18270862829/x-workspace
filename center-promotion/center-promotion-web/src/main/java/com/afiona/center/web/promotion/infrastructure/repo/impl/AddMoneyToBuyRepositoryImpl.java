package com.afiona.center.web.promotion.infrastructure.repo.impl;

import com.afiona.center.client.promotion.constants.PromotionResultEnum;
import com.afiona.center.client.promotion.constants.PromotionType;
import com.afiona.center.client.promotion.domain.aggregate.AddMoneyToBuyAggregate;
import com.afiona.center.client.promotion.model.query.PromotionQuery;
import com.afiona.center.web.promotion.domain.repo.AddMoneyToBuyRepository;
import com.afiona.center.web.promotion.domain.service.aggregate.IAggregate;
import com.afiona.center.web.promotion.domain.service.resolve.IBaseResolve;
import com.afiona.center.web.promotion.domain.service.resolve.IResolve;
import com.afiona.center.web.promotion.infrastructure.dao.AddMoneyToBuyGoodsDAO;
import com.afiona.center.web.promotion.infrastructure.dao.AddMoneyToBuyRuleDAO;
import com.afiona.center.web.promotion.infrastructure.dao.PromotionDAO;
import com.afiona.center.web.promotion.infrastructure.model.AddMoneyToBuyGoodsDO;
import com.afiona.center.web.promotion.infrastructure.model.AddMoneyToBuyRuleDO;
import com.afiona.center.web.promotion.infrastructure.model.PromotionDO;
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
 * 加价购repo实现
 *
 * @author LiJinXing
 * @date 2020/2/22
 */
@Repository
@Transactional(rollbackFor =Throwable.class)
public class AddMoneyToBuyRepositoryImpl implements AddMoneyToBuyRepository {

    @Resource
    private PromotionDAO promotionDAO;

    @Resource
    private IResolve<AddMoneyToBuyAggregate> iResolve;

    @Resource
    private IAggregate<AddMoneyToBuyAggregate> iAggregate;

    @Resource
    private IBaseResolve<AddMoneyToBuyAggregate> iBaseResolve;

    @Resource
    private AddMoneyToBuyRuleDAO addMoneyToBuyRuleDAO;

    @Resource
    private AddMoneyToBuyGoodsDAO addMoneyToBuyGoodsDAO;

    @Override
    public AddMoneyToBuyAggregate get(long promotionId) {
        PromotionDO promotionDO = promotionDAO.getById(promotionId);
        if(promotionDO==null){
            return null;
        }
        List<AddMoneyToBuyAggregate> promotion = CloneUtil.cloneList(Lists.newArrayList(promotionDO), AddMoneyToBuyAggregate.class);
        iAggregate.aggregate(promotion);
        return promotion.get(0);
    }

    @Override
    public PageBean<AddMoneyToBuyAggregate> list(PromotionQuery query) {
        query.setPromotionTypes(Lists.newArrayList(PromotionType.ADD_MONEY_TO_BUY_GOODS));
        PageBean<PromotionDO> pageBean = promotionDAO.listPage(query);
        List<AddMoneyToBuyAggregate> promotions = CloneUtil.cloneList(pageBean.getContent(), AddMoneyToBuyAggregate.class);
        iAggregate.aggregate(promotions);
        return PageBeanUtils.pageBeanCopy(promotions,pageBean);
    }

    @Override
    public List<AddMoneyToBuyAggregate> listAll(PromotionQuery query) {
        query.setPromotionTypes(Lists.newArrayList(PromotionType.ADD_MONEY_TO_BUY_GOODS));
        List<PromotionDO> list = promotionDAO.listAll(query);
        List<AddMoneyToBuyAggregate> promotions = CloneUtil.cloneList(list, AddMoneyToBuyAggregate.class);
        iAggregate.aggregate(promotions);
        return promotions;
    }

    @Override
    public JsonResult store(AddMoneyToBuyAggregate addMoneyToBuyAggregate) {
        addMoneyToBuyAggregate.setType(PromotionType.ADD_MONEY_TO_BUY_GOODS);
        iResolve.resolve(addMoneyToBuyAggregate);
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
        addMoneyToBuyRuleDAO.remove(new LambdaQueryWrapper<AddMoneyToBuyRuleDO>().in(AddMoneyToBuyRuleDO::getPromotionId,ids));
        addMoneyToBuyGoodsDAO.remove(new LambdaQueryWrapper<AddMoneyToBuyGoodsDO>().in(AddMoneyToBuyGoodsDO::getPromotionId,ids));
        return JsonResult.create();
    }
}
