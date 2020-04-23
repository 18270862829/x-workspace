package com.afiona.center.web.promotion.api.impl;

import com.afiona.center.client.promotion.api.PresellApi;
import com.afiona.center.client.promotion.constants.choice.OperatingTypeEnum;
import com.afiona.center.client.promotion.domain.aggregate.PresellAggregate;
import com.afiona.center.client.promotion.domain.model.promotionrule.presell.PresellGoods;
import com.afiona.center.client.promotion.model.query.PromotionQuery;
import com.afiona.center.web.promotion.domain.repo.PresellRepository;
import com.afiona.center.web.promotion.domain.service.verify.PromotionVerify;
import com.afiona.center.web.promotion.infrastructure.dao.PresellGoodsDAO;
import com.afiona.center.web.promotion.infrastructure.model.PresellGoodsDO;
import com.afiona.common.pojo.JsonResult;
import com.afiona.common.util.CloneUtil;
import com.deepexi.util.pageHelper.PageBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;

/**
 * 预售促销管理实现
 *
 * @author LiJinXing
 * @date 2020/4/22
 */
@RestController
public class PresellApiImpl implements PresellApi {

    @Resource
    private PresellRepository presellRepository;

    @Resource
    private PresellGoodsDAO presellGoodsDAO;

    @Resource
    private PromotionVerify promotionVerify;

    @Override
    public JsonResult<PresellAggregate> get(long promotionId) {
        return JsonResult.create(presellRepository.get(promotionId));
    }

    @Override
    public JsonResult<PageBean<PresellAggregate>> list(@RequestBody PromotionQuery query) {
        return JsonResult.create(presellRepository.list(query));
    }

    @Override
    public JsonResult<List<PresellAggregate>> listAll(@RequestBody PromotionQuery query) {
        return JsonResult.create(presellRepository.listAll(query));
    }

    @Override
    public JsonResult store(@RequestBody PresellAggregate presellAggregate) {
        return presellRepository.store(presellAggregate);
    }

    @Override
    public JsonResult remove(@RequestBody Long id) {
        return presellRepository.remove(id);
    }

    @Override
    public JsonResult removeBatch(@RequestBody List<Long> ids) {
        return presellRepository.removeBatch(ids);
    }

    @Override
    public JsonResult removePresellGoods(Long presellGoodsId) {
        statusVerify(presellGoodsId,OperatingTypeEnum.OPERATING_DELETE);
        presellGoodsDAO.removeById(presellGoodsId);
        return JsonResult.create();
    }

    @Override
    public JsonResult updatePresellGoods(PresellGoods presellGoods) {
        statusVerify(presellGoods.getId(),OperatingTypeEnum.OPERATING_UPDATE);
        presellGoodsDAO.updateById(CloneUtil.clone(presellGoods,PresellGoodsDO.class));
        return JsonResult.create();
    }

    private void statusVerify(Long presellGoodsId,OperatingTypeEnum operatingType) {
        PresellGoodsDO dao = presellGoodsDAO.getById(presellGoodsId);
        if(dao==null){
            return ;
        }
        promotionVerify.statusVerify(dao.getPromotionId(), operatingType);

    }
}
