package com.afiona.center.web.promotion.api.impl;

import java.util.List;

import javax.annotation.Resource;

import com.afiona.center.client.promotion.constants.choice.OperatingTypeEnum;
import com.afiona.center.web.promotion.domain.service.verify.PromotionVerify;
import com.afiona.center.web.promotion.infrastructure.dao.PayGiftStairRuleDAO;
import com.afiona.center.web.promotion.infrastructure.model.AddMoneyToBuyGoodsDO;
import com.afiona.center.web.promotion.infrastructure.model.FullReductionStairRuleDO;
import com.afiona.center.web.promotion.infrastructure.model.PayGiftStairRuleDO;
import com.deepexi.util.CollectionUtil;
import com.deepexi.util.pageHelper.PageBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.afiona.center.client.promotion.api.PayGiftApi;
import com.afiona.center.client.promotion.domain.aggregate.PayGiftAggregate;
import com.afiona.center.client.promotion.model.query.PromotionQuery;
import com.afiona.center.web.promotion.domain.repo.PayGiftRepository;
import com.afiona.common.pojo.JsonResult;

/**
 * 支付有礼促销活动管理实现
 *
 * @author LiJinXing
 * @date 2020/2/25
 */
@RestController
public class PayGiftApiImpl implements PayGiftApi {

    @Resource
    private PayGiftRepository payGiftRepository;

    @Resource
    private PromotionVerify promotionVerify;

    @Resource
    private PayGiftStairRuleDAO payGiftStairRuleDAO;

    @Override
    public JsonResult<PayGiftAggregate> get(@RequestParam("promotionId") long promotionId) {
        return JsonResult.create(payGiftRepository.get(promotionId));
    }

    @Override
    public JsonResult<PageBean<PayGiftAggregate>> list(@RequestBody PromotionQuery query) {
        return JsonResult.create(payGiftRepository.list(query));
    }

    @Override
    public JsonResult<List<PayGiftAggregate>> listAll(@RequestBody PromotionQuery query) {
        return JsonResult.create(payGiftRepository.listAll(query));
    }

    @Override
    public JsonResult store(@RequestBody PayGiftAggregate payGiftAggregate) {
        return payGiftRepository.store(payGiftAggregate);
    }

    @Override
    public JsonResult remove(Long id) {
        return payGiftRepository.remove(id);
    }

    @Override
    public JsonResult removeBatch(List<Long> ids) {
        return payGiftRepository.removeBatch(ids);
    }

    @Override
    public JsonResult removePayGiftStairRuleIds(@RequestBody List<Long> payGiftStairRuleIds) {
        statusVerify(payGiftStairRuleIds);
        return payGiftRepository.removePayGiftStairRuleIds(payGiftStairRuleIds);
    }

    private void statusVerify(List<Long> payGiftStairRuleIds) {
        if(CollectionUtil.isEmpty(payGiftStairRuleIds)){
            return;
        }
        PayGiftStairRuleDO dao = payGiftStairRuleDAO.getById(payGiftStairRuleIds.get(0));
        if(dao==null){
            return;
        }
        promotionVerify.statusVerify(dao.getPromotionId(), OperatingTypeEnum.OPERATING_DELETE);

    }
}
