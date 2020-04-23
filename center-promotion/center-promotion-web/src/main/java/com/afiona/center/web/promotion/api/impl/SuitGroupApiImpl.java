package com.afiona.center.web.promotion.api.impl;

import java.util.List;

import javax.annotation.Resource;

import com.afiona.center.client.promotion.constants.choice.OperatingTypeEnum;
import com.afiona.center.web.promotion.domain.service.verify.PromotionVerify;
import com.afiona.center.web.promotion.infrastructure.dao.SuitFixedSuitGoodsDAO;
import com.afiona.center.web.promotion.infrastructure.dao.SuitFixedSuitRuleDAO;
import com.afiona.center.web.promotion.infrastructure.model.SuitFixedSuitGoodsDO;
import com.afiona.center.web.promotion.infrastructure.model.SuitFixedSuitRuleDO;
import com.deepexi.util.pageHelper.PageBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.afiona.center.client.promotion.api.SuitGroupApi;
import com.afiona.center.client.promotion.domain.aggregate.SuitGroupAggregate;
import com.afiona.center.client.promotion.model.query.PromotionQuery;
import com.afiona.center.web.promotion.domain.repo.SuitGroupRepository;
import com.afiona.common.pojo.JsonResult;

/**
 * 组合套装促销活动管理实现
 *
 * @author LiJinXing
 * @date 2020/2/23
 */
@RestController
public class SuitGroupApiImpl implements SuitGroupApi {

    @Resource
    private SuitGroupRepository suitGroupRepository;

    @Resource
    private PromotionVerify promotionVerify;

    @Resource
    private SuitFixedSuitGoodsDAO suitFixedSuitGoodsDAO;

    @Resource
    private SuitFixedSuitRuleDAO suitFixedSuitRuleDAO;

    @Override
    public JsonResult<SuitGroupAggregate> get(@RequestParam("promotionId") long promotionId) {
        return JsonResult.create(suitGroupRepository.get(promotionId));
    }

    @Override
    public JsonResult<PageBean<SuitGroupAggregate>> list(@RequestBody PromotionQuery query) {
        return JsonResult.create(suitGroupRepository.list(query));
    }

    @Override
    public JsonResult<List<SuitGroupAggregate>> listAll(@RequestBody PromotionQuery query) {
        return JsonResult.create(suitGroupRepository.listAll(query));
    }

    @Override
    public JsonResult store(@RequestBody SuitGroupAggregate suitGroupAggregate) {
        return suitGroupRepository.store(suitGroupAggregate);
    }

    @Override
    public JsonResult remove(Long id) {
        return suitGroupRepository.remove(id);
    }

    @Override
    public JsonResult removeBatch(List<Long> ids) {
        return suitGroupRepository.removeBatch(ids);
    }

    @Override
    public JsonResult removeSuitFixedSuitRule(@RequestBody Long suitFixedSuitRuleId) {
        SuitFixedSuitRuleDO dao = suitFixedSuitRuleDAO.getById(suitFixedSuitRuleId);
        if(dao==null){
            return JsonResult.create();
        }
        promotionVerify.statusVerify(dao.getPromotionId(), OperatingTypeEnum.OPERATING_DELETE);
        return suitGroupRepository.removeSuitFixedSuitRule(suitFixedSuitRuleId);
    }

    @Override
    public JsonResult removeSuitFixedSuitGoods(@RequestBody Long suitFixedSuitGoodsId) {
        SuitFixedSuitGoodsDO dao = suitFixedSuitGoodsDAO.getById(suitFixedSuitGoodsId);
        if(dao==null){
            return JsonResult.create();
        }
        promotionVerify.statusVerify(dao.getPromotionId(), OperatingTypeEnum.OPERATING_DELETE);
        return suitGroupRepository.removeSuitFixedSuitGoods(suitFixedSuitGoodsId);
    }
}
