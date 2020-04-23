package com.afiona.center.web.promotion.domain.service.resolve.impl;

import com.afiona.center.client.promotion.constants.PromotionResultEnum;
import com.afiona.center.client.promotion.domain.aggregate.AddMoneyToBuyAggregate;
import com.afiona.center.client.promotion.domain.aggregate.SuitGroupAggregate;
import com.afiona.center.client.promotion.domain.model.promotionrule.suit.SuitFixedSuitGoods;
import com.afiona.center.client.promotion.domain.model.promotionrule.suit.SuitFixedSuitRule;
import com.afiona.center.web.promotion.domain.service.resolve.IBaseResolve;
import com.afiona.center.web.promotion.domain.service.resolve.IResolve;
import com.afiona.center.web.promotion.infrastructure.dao.SuitFixedSuitGoodsDAO;
import com.afiona.center.web.promotion.infrastructure.dao.SuitFixedSuitRuleDAO;
import com.afiona.center.web.promotion.infrastructure.dao.SuitGroupRuleDAO;
import com.afiona.center.web.promotion.infrastructure.model.*;
import com.afiona.common.pojo.JsonResult;
import com.afiona.common.util.CloneUtil;
import com.deepexi.util.CollectionUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 组合套装解析器
 *
 * @author LiJinXing
 * @date 2020/2/19
 */
@Service
public class SuitGroupResolveService  implements IResolve<SuitGroupAggregate> {

    @Resource
    private IBaseResolve<SuitGroupAggregate> baseResolveService;

    @Resource
    private SuitGroupRuleDAO suitGroupRuleDAO;

    @Resource
    private SuitFixedSuitGoodsDAO suitFixedSuitGoodsDAO;

   @Resource
   private SuitFixedSuitRuleDAO suitFixedSuitRuleDAO;

    @Override
    public Long resolve(SuitGroupAggregate promotions) {
        Long promotionId = baseResolveService.resolve(promotions);
        if(promotionId==null){
            JsonResult.create(PromotionResultEnum.DATA_ERROR);
        }
        resolveSuitGroupRule(promotions,promotionId);
        return promotionId;
    }

    private void resolveSuitGroupRule(SuitGroupAggregate promotions, Long promotionId) {
        //组合套装规则
        SuitGroupRuleDO suitGroupRuleDO = CloneUtil.clone(promotions.getSuitGroupRule(), SuitGroupRuleDO.class);
        suitGroupRuleDO.setPromotionId(promotionId);
        suitGroupRuleDAO.saveOrUpdate(suitGroupRuleDO);
        //固定套装规则
        List<SuitFixedSuitRule> suitFixedSuitRules = promotions.getSuitGroupRule().getSuitFixedSuitRules();
        if(CollectionUtil.isEmpty(suitFixedSuitRules)){
            return;
        }
        List<SuitFixedSuitRuleDO> suitFixedSuitRuleDOS = CloneUtil.cloneList(suitFixedSuitRules, SuitFixedSuitRuleDO.class);
        suitFixedSuitRuleDOS.forEach(suitFixedSuitRuleDO -> suitFixedSuitRuleDO.setPromotionId(promotionId));
        suitFixedSuitRuleDAO.saveOrUpdateBatch(suitFixedSuitRuleDOS);

        //固定套装关联商品
        int index =-1;
        for (SuitFixedSuitRuleDO suitFixedSuitRuleDO : suitFixedSuitRuleDOS) {
            index++;
            List<SuitFixedSuitGoods> fixedSuitGoodsList = suitFixedSuitRules.get(index).getFixedSuitGoodsList();
            if(CollectionUtil.isEmpty(fixedSuitGoodsList)){
                continue;
            }
            fixedSuitGoodsList.forEach(suitFixedSuitGoods -> suitFixedSuitGoods.setSuitFixedSuitId(suitFixedSuitRuleDO.getId()));
        }
        List<SuitFixedSuitGoods> list =new ArrayList<>();
        suitFixedSuitRules.stream().map(SuitFixedSuitRule::getFixedSuitGoodsList)
                .filter(CollectionUtil::isNotEmpty)
                .forEach(list::addAll);
        if(CollectionUtil.isEmpty(list)){
            return;
        }
        List<SuitFixedSuitGoodsDO> dos = CloneUtil.cloneList(list, SuitFixedSuitGoodsDO.class);
        dos.forEach(suitFixedSuitGoodsDO -> suitFixedSuitGoodsDO.setPromotionId(promotionId));
        suitFixedSuitGoodsDAO.saveOrUpdateBatch(dos);
    }
}
