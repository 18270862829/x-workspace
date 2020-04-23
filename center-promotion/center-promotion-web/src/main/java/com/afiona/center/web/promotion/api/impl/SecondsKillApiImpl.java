package com.afiona.center.web.promotion.api.impl;

import com.afiona.center.client.promotion.api.SecondsKillApi;
import com.afiona.center.client.promotion.domain.aggregate.SecondsKillAggregate;
import com.afiona.center.client.promotion.model.query.PromotionQuery;
import com.afiona.center.web.promotion.domain.repo.SecondsKillRepository;
import com.afiona.common.pojo.JsonResult;
import com.deepexi.util.pageHelper.PageBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 秒杀管理实现
 *
 * @author LiJinXing
 * @date 2020/4/7
 */
@RestController
public class SecondsKillApiImpl implements SecondsKillApi {

    @Resource
    private SecondsKillRepository secondsKillRepository;

    @Override
    public JsonResult<SecondsKillAggregate> get(long promotionId) {
        return JsonResult.create(secondsKillRepository.get(promotionId));
    }

    @Override
    public JsonResult<PageBean<SecondsKillAggregate>> list(@RequestBody PromotionQuery query) {
        return JsonResult.create(secondsKillRepository.list(query));
    }

    @Override
    public JsonResult<List<SecondsKillAggregate>> listAll(@RequestBody PromotionQuery query) {
        return JsonResult.create(secondsKillRepository.listAll(query));
    }

    @Override
    public JsonResult store(@RequestBody SecondsKillAggregate secondsKillAggregate) {
        return secondsKillRepository.store(secondsKillAggregate);
    }

    @Override
    public JsonResult remove(Long id) {
        return secondsKillRepository.remove(id);
    }

    @Override
    public JsonResult removeBatch(List<Long> ids) {
        return secondsKillRepository.removeBatch(ids);
    }

}
