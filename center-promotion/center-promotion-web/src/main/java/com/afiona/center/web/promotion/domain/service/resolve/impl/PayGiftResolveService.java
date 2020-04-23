package com.afiona.center.web.promotion.domain.service.resolve.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.afiona.center.client.promotion.constants.PromotionResultEnum;
import com.afiona.center.client.promotion.constants.choice.GiftChoiceEnum;
import com.afiona.center.client.promotion.domain.aggregate.AddMoneyToBuyAggregate;
import com.afiona.center.client.promotion.domain.aggregate.PayGiftAggregate;
import com.afiona.center.client.promotion.domain.model.promotionrule.paygift.PayGiftCoupon;
import com.afiona.center.client.promotion.domain.model.promotionrule.paygift.PayGiftGoods;
import com.afiona.center.client.promotion.domain.model.promotionrule.paygift.PayGiftRule;
import com.afiona.center.client.promotion.domain.model.promotionrule.paygift.PayGiftStairRule;
import com.afiona.center.web.promotion.domain.service.resolve.IBaseResolve;
import com.afiona.center.web.promotion.domain.service.resolve.IResolve;
import com.afiona.center.web.promotion.infrastructure.dao.PayGiftCouponDAO;
import com.afiona.center.web.promotion.infrastructure.dao.PayGiftGoodsDAO;
import com.afiona.center.web.promotion.infrastructure.dao.PayGiftRuleDAO;
import com.afiona.center.web.promotion.infrastructure.dao.PayGiftStairRuleDAO;
import com.afiona.center.web.promotion.infrastructure.model.PayGiftCouponDO;
import com.afiona.center.web.promotion.infrastructure.model.PayGiftGoodsDO;
import com.afiona.center.web.promotion.infrastructure.model.PayGiftRuleDO;
import com.afiona.center.web.promotion.infrastructure.model.PayGiftStairRuleDO;
import com.afiona.center.client.promotion.util.JsonUtils;
import com.afiona.common.pojo.JsonResult;
import com.afiona.common.util.CloneUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 支付有礼解析器
 *
 * @author LiJinXing
 * @date 2020/2/25
 */
@Service
public class PayGiftResolveService  implements IResolve<PayGiftAggregate> {

    @Resource
    private IBaseResolve<PayGiftAggregate> baseResolveService;

    @Resource
    private PayGiftRuleDAO payGiftRuleDAO;

    @Resource
    private PayGiftGoodsDAO payGiftGoodsDAO;

    @Resource
    private PayGiftCouponDAO payGiftCouponDAO;

    @Resource
    private PayGiftStairRuleDAO payGiftStairRuleDAO;

    @Override
    public Long resolve(PayGiftAggregate promotion) {
        priorityCalc(promotion);
        Long promotionId = baseResolveService.resolve(promotion);
        if(promotionId==null){
            JsonResult.create(PromotionResultEnum.DATA_ERROR);
        }
        resolvePayGiftRule(promotion,promotionId);
        return promotionId;
    }

    private void resolvePayGiftRule(PayGiftAggregate promotion, Long promotionId) {
        PayGiftRuleDO payGiftRuleDO = CloneUtil.clone(promotion.getPayGiftRule(), PayGiftRuleDO.class);
        payGiftRuleDO.setPromotionId(promotionId);
        payGiftRuleDAO.saveOrUpdate(payGiftRuleDO);
        resolvePayGiftStairRule(promotion.getPayGiftRule().getPayGiftStairRules(),promotionId);
    }

    private void resolvePayGiftStairRule(List<PayGiftStairRule> payGiftStairRules, Long promotionId) {
        List<PayGiftStairRuleDO> payGiftStairRuleDOList = CloneUtil.cloneList(payGiftStairRules, PayGiftStairRuleDO.class);
        int index=-1;
        for (PayGiftStairRuleDO payGiftStairRuleDO : payGiftStairRuleDOList) {
            index++;
            payGiftStairRuleDO.setPromotionId(promotionId);
            BeanUtil.copyProperties(payGiftStairRules.get(index).getPreferentialThreshold(),payGiftStairRuleDO);
            payGiftStairRuleDO.setGiftChoice(JsonUtils.objToJson(payGiftStairRules.get(index).getGiftChoice()));
        }
        payGiftStairRuleDAO.saveOrUpdateBatch(payGiftStairRuleDOList);

        List<PayGiftCouponDO> payGiftCouponDOList =new ArrayList<>();
        List<PayGiftGoodsDO> payGiftGoodsDOList =new ArrayList<>();
        index=-1;
        for (PayGiftStairRuleDO payGiftStairRuleDO : payGiftStairRuleDOList) {
            index++;
            List<PayGiftCoupon> payGiftCoupons = payGiftStairRules.get(index).getPayGiftCoupons();
            for (PayGiftCoupon payGiftCoupon : payGiftCoupons) {
                payGiftCoupon.setPromotionId(promotionId);
                payGiftCoupon.setStairRuleId(payGiftStairRuleDO.getId());
            }
            payGiftCouponDOList.addAll(CloneUtil.cloneList(payGiftCoupons, PayGiftCouponDO.class));
            List<PayGiftGoods> payGiftGoods = payGiftStairRules.get(index).getPayGiftGoods();
            for (PayGiftGoods payGiftGood : payGiftGoods) {
                payGiftGood.setPromotionId(promotionId);
                payGiftGood.setStairRuleId(payGiftStairRuleDO.getId());
            }
            payGiftGoodsDOList.addAll(CloneUtil.cloneList(payGiftGoods, PayGiftGoodsDO.class));
        }
        if(!payGiftCouponDOList.isEmpty()){
            payGiftCouponDAO.saveOrUpdateBatch(payGiftCouponDOList);
        }
        if(!payGiftGoodsDOList.isEmpty()){
            payGiftGoodsDAO.saveOrUpdateBatch(payGiftGoodsDOList);

        }
    }

    public void priorityCalc(PayGiftAggregate payGiftAggregate){
        PayGiftRule payGiftRule = payGiftAggregate.getPayGiftRule();
        if(payGiftRule==null){
            return;
        }
        List<PayGiftStairRule> payGiftStairRules = payGiftRule.getPayGiftStairRules();
        if(CollectionUtil.isEmpty(payGiftStairRules)){
            return;
        }
        List<GiftChoiceEnum> giftChoiceList = payGiftStairRules.get(0).getGiftChoice();
        if(CollectionUtil.isEmpty(giftChoiceList)){
            return;
        }
        Long priority = giftChoiceList.stream().mapToLong(GiftChoiceEnum::getPriority).sum();
        payGiftAggregate.setPriority(priority);
    }

}
