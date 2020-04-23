package com.afiona.center.web.promotion.domain.service.resolve.impl;

import cn.hutool.core.bean.BeanUtil;
import com.afiona.center.client.promotion.constants.PromotionResultEnum;
import com.afiona.center.client.promotion.constants.choice.AmountChoiceEnum;
import com.afiona.center.client.promotion.constants.choice.ContentTypeChoiceEnum;
import com.afiona.center.client.promotion.constants.choice.GiftChoiceEnum;
import com.afiona.center.client.promotion.domain.aggregate.AddMoneyToBuyAggregate;
import com.afiona.center.client.promotion.domain.aggregate.FullReductionAggregate;
import com.afiona.center.client.promotion.domain.model.promotionrule.fullreduction.FullReductionStairRule;
import com.afiona.center.web.promotion.domain.service.resolve.IBaseResolve;
import com.afiona.center.web.promotion.domain.service.resolve.IResolve;
import com.afiona.center.web.promotion.infrastructure.dao.FullReductionCouponDAO;
import com.afiona.center.web.promotion.infrastructure.dao.FullReductionGitGoodsDAO;
import com.afiona.center.web.promotion.infrastructure.dao.FullReductionRuleDAO;
import com.afiona.center.web.promotion.infrastructure.dao.FullReductionStairRuleDAO;
import com.afiona.center.web.promotion.infrastructure.model.*;
import com.afiona.center.client.promotion.util.JsonUtils;
import com.afiona.common.pojo.JsonResult;
import com.afiona.common.util.CloneUtil;
import com.deepexi.util.CollectionUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 满减促销活动解析器
 *
 * @author dengweiyi
 * @date 2020-02-12
 */
@Service
public class FullReductionResolveService  implements IResolve<FullReductionAggregate> {

    @Resource
    private IBaseResolve<FullReductionAggregate> baseResolveService;

    @Resource
    private FullReductionRuleDAO fullReductionRuleDAO;

    @Resource
    private FullReductionStairRuleDAO fullReductionStairRuleDAO;

    @Resource
    private FullReductionCouponDAO fullReductionCouponDAO;

    @Resource
    private FullReductionGitGoodsDAO fullReductionGitGoodsDAO;

    @Override
    public Long resolve(FullReductionAggregate promotions){
        priorityCalc(promotions);
        Long promotionId = baseResolveService.resolve(promotions);
        if(promotionId==null){
            JsonResult.create(PromotionResultEnum.DATA_ERROR);
        }
        resolveFullReductionRule(promotions,promotionId);
        return promotionId;
    }

    private void resolveFullReductionRule(FullReductionAggregate promotions, Long promotionId) {
        //满减规则DO
        FullReductionRuleDO fullReductionRuleDO = CloneUtil.clone(promotions.getFullReductionRule(), FullReductionRuleDO.class);
            fullReductionRuleDO.setPromotionId(promotionId);
        fullReductionRuleDAO.saveOrUpdate(fullReductionRuleDO);

        List<FullReductionCouponDO> fullReductionCouponDOList =new ArrayList<>();
        List<FullReductionGitGoodsDO> fullReductionGitGoodsDOList =new ArrayList<>();

        List<FullReductionStairRuleDO> fullReductionStairRuleDOS = CloneUtil.cloneList(promotions.getFullReductionRule().getFullReductionStairRules(), FullReductionStairRuleDO.class);
        int i =-1;
        for (FullReductionStairRuleDO fullReductionStairRuleDO : fullReductionStairRuleDOS) {
            i++;
            //阶梯满减规则DO
            fullReductionStairRuleDO.setPromotionId(promotionId);
            FullReductionStairRule fullReductionStairRule = promotions.getFullReductionRule().getFullReductionStairRules().get(i);
            BeanUtil.copyProperties(fullReductionStairRule.getPreferentialThreshold(),fullReductionStairRuleDO);
            BeanUtil.copyProperties(fullReductionStairRule.getPreferentialContent(),fullReductionStairRuleDO);
            BeanUtil.copyProperties(fullReductionStairRule.getPreferentialContent().getAmount(),fullReductionStairRuleDO);
            BeanUtil.copyProperties(fullReductionStairRule.getPreferentialContent().getGift(),fullReductionStairRuleDO);
            fullReductionStairRuleDO.setContentTypeChoice(JsonUtils.objToJson(fullReductionStairRule.getPreferentialContent().getContentTypeChoices()));

        }
        fullReductionStairRuleDAO.saveOrUpdateBatch(fullReductionStairRuleDOS);
        int index =-1;
        for (FullReductionStairRuleDO fullReductionStairRuleDO : fullReductionStairRuleDOS) {
            index++;
            //满减促销活动关联优惠劵DO
            FullReductionStairRule.Gift gift = promotions.getFullReductionRule().getFullReductionStairRules().get(index).getPreferentialContent().getGift();
            if(gift!=null){
                List<FullReductionCouponDO> fullReductionCouponDOS = CloneUtil.cloneList(promotions.getFullReductionRule().getFullReductionStairRules().get(index).getPreferentialContent().getGift().getFullReductionCoupons(), FullReductionCouponDO.class);
                if(CollectionUtil.isNotEmpty(fullReductionCouponDOS)){
                    fullReductionCouponDOS.forEach(fullReductionCouponDO -> {
                        fullReductionCouponDO.setPromotionId(promotionId);
                        fullReductionCouponDO.setStairRuleId(fullReductionStairRuleDO.getId());
                    });
                    fullReductionCouponDOList.addAll(fullReductionCouponDOS);
                }
                //满减促销活动关联赠品DO
                List<FullReductionGitGoodsDO> fullReductionGitGoodsDOS = CloneUtil.cloneList(promotions.getFullReductionRule().getFullReductionStairRules().get(index).getPreferentialContent().getGift().getFullReductionGitGoods(), FullReductionGitGoodsDO.class);
                if(CollectionUtil.isNotEmpty(fullReductionGitGoodsDOS)){
                    fullReductionGitGoodsDOS.forEach(fullReductionGitGoodsDO -> {
                        fullReductionGitGoodsDO.setPromotionId(promotionId);
                        fullReductionGitGoodsDO.setStairRuleId(fullReductionStairRuleDO.getId());
                    });
                    fullReductionGitGoodsDOList.addAll(fullReductionGitGoodsDOS);
                }

            }
            if(!fullReductionCouponDOList.isEmpty()){
                fullReductionCouponDAO.saveOrUpdateBatch(fullReductionCouponDOList);

            }
            if(!fullReductionGitGoodsDOList.isEmpty()){
                fullReductionGitGoodsDAO.saveOrUpdateBatch(fullReductionGitGoodsDOList);
            }
        }

    }

    private void priorityCalc(FullReductionAggregate fullReductionAggregate){
        List<FullReductionStairRule> fullReductionStairRules = fullReductionAggregate.getFullReductionRule().getFullReductionStairRules();
        if(CollectionUtil.isEmpty(fullReductionStairRules)){
            return;
        }
        FullReductionStairRule fullReductionStairRule = fullReductionStairRules.get(0);
        FullReductionStairRule.PreferentialContent preferentialContent = fullReductionStairRule.getPreferentialContent();
        if(preferentialContent==null){
            return;
        }
        List<ContentTypeChoiceEnum> contentTypeChoices = preferentialContent.getContentTypeChoices();
        if(CollectionUtil.isEmpty(contentTypeChoices)){
            return;
        }
        Long priority = contentTypeChoices.stream().mapToLong(ContentTypeChoiceEnum::getPriority).sum();
        FullReductionStairRule.Amount amount = preferentialContent.getAmount();
       if(amount!=null&&amount.getAmountChoice()!=null){
           AmountChoiceEnum amountChoice = amount.getAmountChoice();
           priority+=amountChoice.getPriority();
       }
        FullReductionStairRule.Gift gift = preferentialContent.getGift();
       if(gift!=null&&gift.getGiftChoice()!=null){
           GiftChoiceEnum choice = gift.getGiftChoice();
           priority+=choice.getPriority();
       }
        fullReductionAggregate.setPriority(priority);
    }

}
