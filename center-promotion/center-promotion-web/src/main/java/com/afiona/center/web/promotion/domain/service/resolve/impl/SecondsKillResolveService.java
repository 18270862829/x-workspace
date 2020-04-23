package com.afiona.center.web.promotion.domain.service.resolve.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.afiona.center.client.promotion.constants.PromotionResultEnum;
import com.afiona.center.client.promotion.domain.aggregate.AddMoneyToBuyAggregate;
import com.afiona.center.client.promotion.domain.aggregate.SecondsKillAggregate;
import com.afiona.center.client.promotion.domain.model.promotionrule.seckill.SecondsKillGoods;
import com.afiona.center.client.promotion.domain.model.promotionrule.seckill.SecondsKillRule;
import com.afiona.center.web.promotion.domain.service.resolve.IBaseResolve;
import com.afiona.center.web.promotion.domain.service.resolve.IResolve;
import com.afiona.center.web.promotion.infrastructure.dao.SecondsKillGoodsDAO;
import com.afiona.center.web.promotion.infrastructure.dao.SecondsKillRuleDAO;
import com.afiona.center.web.promotion.infrastructure.model.SecondsKillGoodsDO;
import com.afiona.center.web.promotion.infrastructure.model.SecondsKillRuleDO;
import com.afiona.common.pojo.JsonResult;
import com.afiona.common.util.CloneUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 秒杀解析器
 *
 * @author LiJinXing
 * @date 2020/4/7
 */
@Service
public class SecondsKillResolveService  implements IResolve<SecondsKillAggregate> {

    @Resource
    private IBaseResolve<SecondsKillAggregate> baseResolveService;

    @Resource
    private SecondsKillGoodsDAO secondsKillGoodsDAO;

    @Resource
    private SecondsKillRuleDAO secondsKillRuleDAO;

    @Override
    public Long resolve(SecondsKillAggregate secondsKillAggregate) {
        Long promotionId = baseResolveService.resolve(secondsKillAggregate);
        if(promotionId==null){
            JsonResult.create(PromotionResultEnum.DATA_ERROR);
        }
        resolveSecondsKillRule(secondsKillAggregate,promotionId);
        return promotionId;
    }

    private void resolveSecondsKillRule(SecondsKillAggregate secondsKillAggregate, Long promotionId) {
        //秒杀规则
        SecondsKillRuleDO secondsKillRuleDO = CloneUtil.clone(secondsKillAggregate.getSecondsKillRule(), SecondsKillRuleDO.class);
        SecondsKillRule.GlobalDiscountSetting globalDiscountSetting = secondsKillAggregate.getSecondsKillRule().getGlobalDiscountSetting();
        if(globalDiscountSetting!=null){
            BeanUtils.copyProperties(globalDiscountSetting,secondsKillRuleDO);
        }
        secondsKillRuleDO.setPromotionId(promotionId);
        secondsKillRuleDAO.saveOrUpdate(secondsKillRuleDO);
        //秒杀-参与商品
        List<SecondsKillGoods> secondsKillGoodsList = secondsKillAggregate.getSecondsKillRule().getSecondsKillGoodsList();
        if(CollectionUtil.isEmpty(secondsKillGoodsList)){
           return;
        }
        List<SecondsKillGoodsDO> list = CloneUtil.cloneList(secondsKillGoodsList, SecondsKillGoodsDO.class);
        list.forEach(secondsKillGoodsDO -> secondsKillGoodsDO.setPromotionId(promotionId));
        secondsKillGoodsDAO.saveOrUpdateBatch(list);

    }
}
