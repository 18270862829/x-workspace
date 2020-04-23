package com.afiona.center.web.promotion.api.impl;

import com.afiona.center.client.promotion.api.PromotionApi;
import com.afiona.center.client.promotion.domain.aggregate.PromotionAggregate;
import com.afiona.center.client.promotion.domain.model.Promotion;
import com.afiona.center.client.promotion.model.*;
import com.afiona.center.client.promotion.model.put.PromotionEnabledSettingPut;
import com.afiona.center.client.promotion.model.query.*;
import com.afiona.center.web.promotion.domain.service.aggregate.impl.BaseAggregateService;
import com.afiona.center.web.promotion.domain.service.benefit.GoodsBenefitCalcService;
import com.afiona.center.web.promotion.domain.service.benefit.PayGiftBenefitService;
import com.afiona.center.web.promotion.domain.service.benefit.verify.SuitRuleVerify;
import com.afiona.center.web.promotion.domain.service.promotion.OptimalPromotionService;
import com.afiona.center.web.promotion.domain.service.promotion.PromotionBenefitsCalcService;
import com.afiona.center.web.promotion.domain.service.promotion.PromotionDetailsService;
import com.afiona.center.web.promotion.domain.service.promotion.PromotionGoodsService;
import com.afiona.center.web.promotion.infrastructure.dao.PromotionDAO;
import com.afiona.center.web.promotion.infrastructure.model.PromotionDO;
import com.afiona.center.client.promotion.util.PageBeanUtils;
import com.afiona.common.pojo.JsonResult;
import com.afiona.common.util.CloneUtil;
import com.deepexi.util.pageHelper.PageBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * 促销活动管理实现
 *
 * @author dengweiyi
 * @date 2020-02-10
 */
@RestController
public class PromotionApiImpl implements PromotionApi {

    @Resource
    private PromotionDetailsService promotionDetailsService;

    @Resource
    private GoodsBenefitCalcService goodsBenefitCalcService;

    @Resource
    private PromotionDAO promotionDAO;

    @Resource
    private PayGiftBenefitService payGiftBenefitService;

    @Resource
    private OptimalPromotionService optimalPromotionService;

    @Resource
    private PromotionBenefitsCalcService promotionBenefitsCalcService;

    @Resource
    private BaseAggregateService baseAggregateService;

    @Resource
    private PromotionGoodsService promotionGoodsService;

    @Override
    public JsonResult<Promotion> get(Long promotionId) {
        return JsonResult.create(CloneUtil.clone(promotionDAO.getById(promotionId),Promotion.class));
    }

    @Override
    public JsonResult<List<PromotionGroup>> listGroupBySkuIds(@RequestBody List<Long> skuIds) {
        return JsonResult.create(promotionDetailsService.listGroupBySkuIds(new HashSet<>(skuIds)));
    }

    @Override
    public JsonResult<Map<Long, List<Promotion>>> listSinglePromotionBySkuIds(List<Long> skuIds) {
        return JsonResult.create(promotionDetailsService.listSinglePromotionBySkuIds(skuIds));
    }

    @Override
    public JsonResult<PromotionAggregate> details(@RequestParam("promotionId")long promotionId) {
        return JsonResult.create(promotionDetailsService.details(promotionId));
    }

    @Override
    public JsonResult<PageBean<Promotion>> list(@RequestBody PromotionQuery query) {
        PageBean<PromotionDO> pageBean = promotionDAO.listPage(query);
        List<Promotion> promotions = CloneUtil.cloneList(pageBean.getContent(), Promotion.class);
        baseAggregateService.aggregateActivity(promotions);
        return JsonResult.create(PageBeanUtils.pageBeanCopy(promotions,pageBean));
    }

    @Override
    public JsonResult<List<Promotion>> listAll(@RequestBody PromotionQuery query) {
        List<Promotion> promotions = CloneUtil.cloneList(promotionDAO.listAll(query), Promotion.class);
        baseAggregateService.aggregateActivity(promotions);
        return JsonResult.create(promotions);
    }

    @Override
    public JsonResult<List<SingleCommoditiesBenefit>> goodsBenefit(@RequestBody GoodsBenefitCalcQuery query) {
        return goodsBenefitCalcService.benefitCalc(query);
    }

    @Override
    public JsonResult<List<PayGiftBenefit>> payGiftBenefit(@RequestBody PayGiftBenefitCalcQuery query) {
        return JsonResult.create(payGiftBenefitService.benefit(query));
    }

    @Override
    public JsonResult enabledSetting(@RequestBody PromotionEnabledSettingPut put) {
        return promotionDetailsService.enabledSetting(put);
    }

    @Override
    public JsonResult<Promotion> optimalPromotion(Long skuId) {
        return JsonResult.create(optimalPromotionService.optimalPromotion(skuId));
    }

    @Override
    public JsonResult<Map<Long, Promotion>> optimalPromotion(@RequestBody List<Long> skuIds) {
        return JsonResult.create(optimalPromotionService.optimalPromotion(skuIds));
    }

    @Override
    public JsonResult<List<Promotion>> promotionListBySkuId(Long skuId) {
        return JsonResult.create(optimalPromotionService.promotionListBySkuId(skuId));
    }

    @Override
    public JsonResult<Map<Long, List<Promotion>>> promotionListBySkuIds(@RequestBody List<Long> skuIds) {
        return JsonResult.create(optimalPromotionService.promotionListBySkuIds(skuIds));
    }

    @Override
    public JsonResult<MaterialBenefits> preferentialCalculation(@RequestBody BenefitCalcQuery query) {
        return promotionBenefitsCalcService.benefitsCalc(query);
    }

    @Override
    public JsonResult<PromotionSuitGoods> getPromotionSuitGoods(PromotionSuitGoodsQuery query) {
        Promotion promotion = get(query.getPromotionId()).getData();
        PromotionAggregate promotionAggregate = CloneUtil.clone(promotion, PromotionAggregate.class);
        //适用渠道校验
        if(!SuitRuleVerify.suitChannelVerify(promotionAggregate.getSuitRule(),query.getChannelId())){
            return JsonResult.create(4000,"该促销不适用于该渠道");
        }
        //适用会员校验
        if(!SuitRuleVerify.suitMemberVerify(promotionAggregate.getSuitRule(),query.getMemberId())){
            return JsonResult.create(4000,"该促销不适用于该会员");
        }
        return promotionGoodsService.suitGoods(promotionAggregate);

    }

}
