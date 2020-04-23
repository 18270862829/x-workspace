package com.afiona.center.web.promotion.api.impl;

import java.util.List;

import javax.annotation.Resource;

import com.afiona.center.client.promotion.constants.choice.OperatingTypeEnum;
import com.afiona.center.web.promotion.domain.service.verify.PromotionVerify;
import com.afiona.center.web.promotion.infrastructure.dao.SpecialDiscountGoodsDAO;
import com.afiona.center.web.promotion.infrastructure.model.PayGiftStairRuleDO;
import com.afiona.center.web.promotion.infrastructure.model.SpecialDiscountGoodsDO;
import com.deepexi.util.CollectionUtil;
import com.deepexi.util.pageHelper.PageBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.afiona.center.client.promotion.api.SpecialDiscountApi;
import com.afiona.center.client.promotion.domain.aggregate.SpecialDiscountAggregate;
import com.afiona.center.client.promotion.model.query.PromotionQuery;
import com.afiona.center.web.promotion.domain.repo.SpecialDiscountRepository;
import com.afiona.common.pojo.JsonResult;

/**
 * 限时折扣促销活动管理实现
 *
 * @author LiJinXing
 * @date 2020/2/23
 */
@RestController
public class SpecialDiscountApiImpl implements SpecialDiscountApi {

    @Resource
    private SpecialDiscountRepository specialDiscountRepository;

    @Resource
    private SpecialDiscountGoodsDAO specialDiscountGoodsDAO;

    @Resource
    private PromotionVerify promotionVerify;

    @Override
    public JsonResult<SpecialDiscountAggregate> get(@RequestParam("promotionId") long promotionId) {
        return JsonResult.create(specialDiscountRepository.get(promotionId));
    }

    @Override
    public JsonResult<PageBean<SpecialDiscountAggregate>> list(@RequestBody PromotionQuery query) {
        return JsonResult.create(specialDiscountRepository.list(query));
    }

    @Override
    public JsonResult<List<SpecialDiscountAggregate>> listAll(@RequestBody PromotionQuery query) {
        return JsonResult.create(specialDiscountRepository.listAll(query));
    }

    @Override
    public JsonResult store(@RequestBody SpecialDiscountAggregate specialDiscountAggregate) {
        return specialDiscountRepository.store(specialDiscountAggregate);
    }

    @Override
    public JsonResult remove(Long id) {
        return specialDiscountRepository.remove(id);
    }

    @Override
    public JsonResult removeBatch(List<Long> ids) {
        return specialDiscountRepository.removeBatch(ids);
    }

    @Override
    public JsonResult removeSpecialDiscountGoodsIds(@RequestBody List<Long> specialDiscountGoodsIds) {
        statusVerify(specialDiscountGoodsIds);
        specialDiscountGoodsDAO.removeByIds(specialDiscountGoodsIds);
        return JsonResult.create();
    }

    private void statusVerify(List<Long> specialDiscountGoodsIds) {
        if(CollectionUtil.isEmpty(specialDiscountGoodsIds)){
            return;
        }
        SpecialDiscountGoodsDO dao = specialDiscountGoodsDAO.getById(specialDiscountGoodsIds.get(0));
        if(dao==null){
            return;
        }
        promotionVerify.statusVerify(dao.getPromotionId(), OperatingTypeEnum.OPERATING_DELETE);

    }
}
