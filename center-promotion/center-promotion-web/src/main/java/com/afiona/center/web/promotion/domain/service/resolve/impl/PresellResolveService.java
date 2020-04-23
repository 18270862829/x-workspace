package com.afiona.center.web.promotion.domain.service.resolve.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.afiona.center.client.promotion.constants.PromotionResultEnum;
import com.afiona.center.client.promotion.domain.aggregate.PresellAggregate;
import com.afiona.center.client.promotion.domain.model.promotionrule.presell.PresellGoods;
import com.afiona.center.web.promotion.domain.service.resolve.IBaseResolve;
import com.afiona.center.web.promotion.domain.service.resolve.IResolve;
import com.afiona.center.web.promotion.infrastructure.dao.PresellGoodsDAO;
import com.afiona.center.web.promotion.infrastructure.dao.PresellRuleDAO;
import com.afiona.center.web.promotion.infrastructure.model.PresellGoodsDO;
import com.afiona.center.web.promotion.infrastructure.model.PresellRuleDO;
import com.afiona.common.pojo.JsonResult;
import com.afiona.common.util.CloneUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 预售解析器
 *
 * @author LiJinXing
 * @date 2020/4/22
 */
@Service
public class PresellResolveService implements IResolve<PresellAggregate> {

    @Resource
    private IBaseResolve<PresellAggregate> iBaseResolve;

    @Resource
    private PresellRuleDAO presellRuleDAO;

    @Resource
    private PresellGoodsDAO presellGoodsDAO;

    @Override
    public Long resolve(PresellAggregate promotionAggregate) {
        Long promotionId = iBaseResolve.resolve(promotionAggregate);
        if(promotionId==null){
            JsonResult.create(PromotionResultEnum.DATA_ERROR);
        }
        resolvePresellRule(promotionAggregate,promotionId);
        return promotionId;
    }

    private void resolvePresellRule(PresellAggregate promotionAggregate,Long promotionId) {
        PresellRuleDO presellRuleDO = CloneUtil.clone(promotionAggregate.getPresellRule(), PresellRuleDO.class);
        presellRuleDO.setPromotionId(promotionId);
        presellRuleDAO.saveOrUpdate(presellRuleDO);

        List<PresellGoods> presellGoodsList = promotionAggregate.getPresellRule().getPresellGoodsList();
        if(CollectionUtil.isEmpty(presellGoodsList)){
            return;
        }
        List<PresellGoodsDO> presellGoods = CloneUtil.cloneList(presellGoodsList, PresellGoodsDO.class);
        presellGoods.forEach(presellGoodsDO -> presellGoodsDO.setPromotionId(promotionId));
        presellGoodsDAO.saveOrUpdateBatch(presellGoods);
    }
}
