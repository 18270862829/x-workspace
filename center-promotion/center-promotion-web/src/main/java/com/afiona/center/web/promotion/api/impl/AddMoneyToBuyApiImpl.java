package com.afiona.center.web.promotion.api.impl;

import com.afiona.center.client.promotion.api.AddMoneyToBuyApi;
import com.afiona.center.client.promotion.constants.choice.OperatingTypeEnum;
import com.afiona.center.client.promotion.domain.aggregate.AddMoneyToBuyAggregate;
import com.afiona.center.client.promotion.model.query.PromotionQuery;
import com.afiona.center.web.promotion.domain.repo.AddMoneyToBuyRepository;
import com.afiona.center.web.promotion.domain.service.verify.PromotionVerify;
import com.afiona.center.web.promotion.infrastructure.dao.AddMoneyToBuyGoodsDAO;
import com.afiona.center.web.promotion.infrastructure.model.AddMoneyToBuyGoodsDO;
import com.afiona.common.pojo.JsonResult;
import com.deepexi.util.CollectionUtil;
import com.deepexi.util.pageHelper.PageBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 加价购促销活动管理实现
 *
 * @author LiJinXing
 * @date 2020/2/23
 */
@RestController
public class AddMoneyToBuyApiImpl implements AddMoneyToBuyApi {

    @Resource
    private AddMoneyToBuyRepository addMoneyToBuyRepository;

    @Resource
    private AddMoneyToBuyGoodsDAO addMoneyToBuyGoodsDAO;

    @Resource
    private PromotionVerify promotionVerify;

    @Override
    public JsonResult<AddMoneyToBuyAggregate> get(@RequestParam("promotionId") long promotionId) {
        return JsonResult.create(addMoneyToBuyRepository.get(promotionId));
    }

    @Override
    public JsonResult<PageBean<AddMoneyToBuyAggregate>> list(@RequestBody PromotionQuery query) {
        return JsonResult.create(addMoneyToBuyRepository.list(query));
    }

    @Override
    public JsonResult<List<AddMoneyToBuyAggregate>> listAll(@RequestBody PromotionQuery query) {
        return JsonResult.create(addMoneyToBuyRepository.listAll(query));
    }

    @Override
    public JsonResult store(@RequestBody AddMoneyToBuyAggregate addMoneyToBuyAggregate) {
        return addMoneyToBuyRepository.store(addMoneyToBuyAggregate);
    }

    @Override
    public JsonResult remove(@RequestBody Long id) {
        return addMoneyToBuyRepository.remove(id);
    }

    @Override
    public JsonResult removeBatch(@RequestBody List<Long> ids) {
        return addMoneyToBuyRepository.removeBatch(ids);
    }

    @Override
    public JsonResult removeAddMoneyToBuyGoodsIds(@RequestBody List<Long> addMoneyToBuyGoodsIds) {
        statusVerify(addMoneyToBuyGoodsIds);
        addMoneyToBuyGoodsDAO.removeByIds(addMoneyToBuyGoodsIds);
        return JsonResult.create();
    }

    private void statusVerify(List<Long> addMoneyToBuyGoodsIds) {
        if(CollectionUtil.isEmpty(addMoneyToBuyGoodsIds)){
            return;
        }
        AddMoneyToBuyGoodsDO dao = addMoneyToBuyGoodsDAO.getById(addMoneyToBuyGoodsIds.get(0));
        if(dao==null){
            return;
        }
        promotionVerify.statusVerify(dao.getPromotionId(), OperatingTypeEnum.OPERATING_DELETE);

    }
}
