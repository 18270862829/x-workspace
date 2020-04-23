package com.afiona.center.web.promotion.api.impl;

import com.afiona.center.client.promotion.api.FullReductionApi;
import com.afiona.center.client.promotion.constants.choice.OperatingTypeEnum;
import com.afiona.center.client.promotion.domain.aggregate.FullReductionAggregate;
import com.afiona.center.client.promotion.model.query.PromotionQuery;
import com.afiona.center.web.promotion.domain.repo.FullReductionRepository;
import com.afiona.center.web.promotion.domain.service.verify.PromotionVerify;
import com.afiona.center.web.promotion.infrastructure.dao.FullReductionStairRuleDAO;
import com.afiona.center.web.promotion.infrastructure.model.FixedPriceGoodsDO;
import com.afiona.center.web.promotion.infrastructure.model.FullReductionStairRuleDO;
import com.afiona.common.pojo.JsonResult;
import com.deepexi.util.CollectionUtil;
import com.deepexi.util.pageHelper.PageBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 满减促销活动管理实现
 *
 * @author dengweiyi
 * @date 2020-02-10
 */
@RestController
public class FullReductionApiImpl implements FullReductionApi {

    @Resource
    private FullReductionRepository fullReductionRepository;

    @Resource
    private FullReductionStairRuleDAO fullReductionStairRuleDAO;

    @Resource
    private PromotionVerify promotionVerify;

    @Override
    public JsonResult<FullReductionAggregate> get(@RequestParam("promotionId") long promotionId) {
        return JsonResult.create(fullReductionRepository.get(promotionId));
    }

    @Override
    public JsonResult<PageBean<FullReductionAggregate>> list(@RequestBody PromotionQuery query) {
        return JsonResult.create(fullReductionRepository.list(query));
    }

    @Override
    public JsonResult<List<FullReductionAggregate>> listAll(@RequestBody PromotionQuery query) {
        return JsonResult.create(fullReductionRepository.listAll(query));
    }

    @Override
    public JsonResult store(@RequestBody FullReductionAggregate fullReductionAggregate) {
        return fullReductionRepository.store(fullReductionAggregate);
    }

    @Override
    public JsonResult remove(Long id) {
        return fullReductionRepository.remove(id);
    }

    @Override
    public JsonResult removeBatch(List<Long> ids) {
        return fullReductionRepository.removeBatch(ids);
    }

    @Override
    public JsonResult removeFullReductionStairRule(@RequestBody Long id) {
        statusVerify(id);
        return fullReductionRepository.removeFullReductionStairRule(id);
    }

    private void statusVerify(Long id) {
        FullReductionStairRuleDO dao = fullReductionStairRuleDAO.getById(id);
        if(dao==null){
            return;
        }
        promotionVerify.statusVerify(dao.getPromotionId(), OperatingTypeEnum.OPERATING_DELETE);

    }
}
