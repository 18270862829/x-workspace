package com.afiona.center.web.promotion.api.impl;

import com.afiona.center.client.promotion.api.FixedPriceApi;
import com.afiona.center.client.promotion.constants.choice.OperatingTypeEnum;
import com.afiona.center.client.promotion.domain.aggregate.FixedPriceAggregate;
import com.afiona.center.client.promotion.model.query.PromotionQuery;
import com.afiona.center.web.promotion.domain.repo.FixedPriceRepository;
import com.afiona.center.web.promotion.domain.service.verify.PromotionVerify;
import com.afiona.center.web.promotion.infrastructure.dao.FixedPriceGoodsDAO;
import com.afiona.center.web.promotion.infrastructure.model.AddMoneyToBuyGoodsDO;
import com.afiona.center.web.promotion.infrastructure.model.FixedPriceGoodsDO;
import com.afiona.common.pojo.JsonResult;

import com.deepexi.util.CollectionUtil;
import com.deepexi.util.pageHelper.PageBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 一口价促销活动管理实现
 *
 * @author LiJinXing
 * @date 2020/2/23
 */
@RestController
public class FixedPriceApiImpl implements FixedPriceApi {

    @Resource
    private FixedPriceRepository fixedPriceRepository;

    @Resource
    private FixedPriceGoodsDAO fixedPriceGoodsDAO;

    @Resource
    private PromotionVerify promotionVerify;

    @Override
    public JsonResult<FixedPriceAggregate> get(@RequestParam("promotionId") long promotionId) {
        return JsonResult.create(fixedPriceRepository.get(promotionId));
    }

    @Override
    public JsonResult<PageBean<FixedPriceAggregate>> list(@RequestBody PromotionQuery query) {
        return JsonResult.create(fixedPriceRepository.list(query));
    }

    @Override
    public JsonResult<List<FixedPriceAggregate>> listAll(@RequestBody PromotionQuery query) {
        return JsonResult.create(fixedPriceRepository.listAll(query));
    }

    @Override
    public JsonResult store(@RequestBody FixedPriceAggregate fixedPriceAggregate) {
        return fixedPriceRepository.store(fixedPriceAggregate);
    }

    @Override
    public JsonResult remove(@RequestBody Long id) {
        return fixedPriceRepository.remove(id);
    }

    @Override
    public JsonResult removeBatch(@RequestBody List<Long> ids) {
        return fixedPriceRepository.removeBatch(ids);
    }

    @Override
    public JsonResult removeFixedPriceGoodsIds(@RequestBody List<Long> fixedPriceGoodsIds) {
        statusVerify(fixedPriceGoodsIds);
        fixedPriceGoodsDAO.removeByIds(fixedPriceGoodsIds);
        return JsonResult.create();
    }

    private void statusVerify(List<Long> fixedPriceGoodsIds) {
        if(CollectionUtil.isEmpty(fixedPriceGoodsIds)){
            return;
        }
        FixedPriceGoodsDO dao = fixedPriceGoodsDAO.getById(fixedPriceGoodsIds.get(0));
        if(dao==null){
            return;
        }
        promotionVerify.statusVerify(dao.getPromotionId(), OperatingTypeEnum.OPERATING_DELETE);

    }
}
