package com.afiona.center.web.promotion.domain.service.resolve.impl;

import cn.hutool.core.bean.BeanUtil;
import com.afiona.center.client.promotion.constants.PromotionResultEnum;
import com.afiona.center.client.promotion.domain.aggregate.AddMoneyToBuyAggregate;
import com.afiona.center.client.promotion.domain.aggregate.SpecialDiscountAggregate;
import com.afiona.center.web.promotion.domain.service.resolve.IBaseResolve;
import com.afiona.center.web.promotion.domain.service.resolve.IResolve;
import com.afiona.center.web.promotion.infrastructure.dao.SpecialDiscountGoodsDAO;
import com.afiona.center.web.promotion.infrastructure.dao.SpecialDiscountRuleDAO;
import com.afiona.center.web.promotion.infrastructure.model.SpecialDiscountGoodsDO;
import com.afiona.center.web.promotion.infrastructure.model.SpecialDiscountRuleDO;
import com.afiona.common.pojo.JsonResult;
import com.afiona.common.util.CloneUtil;
import com.deepexi.util.CollectionUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 限时折扣促销活动解析器
 *
 * @author LiJinXing
 * @date 2020/2/18
 */
@Service
public class SpecialDiscountResolveService  implements IResolve<SpecialDiscountAggregate> {

    @Resource
    private IBaseResolve<SpecialDiscountAggregate> baseResolveService;

    @Resource
    private SpecialDiscountRuleDAO specialDiscountRuleDAO;

    @Resource
    private SpecialDiscountGoodsDAO specialDiscountGoodsDAO;

    @Override
    public Long resolve(SpecialDiscountAggregate specialDiscountAggregate) {
        Long promotionId = baseResolveService.resolve(specialDiscountAggregate);
        if(promotionId==null){
            JsonResult.create(PromotionResultEnum.DATA_ERROR);
        }
        resolveSpecialDiscountRule(specialDiscountAggregate,promotionId);
        return promotionId;
    }

    private void resolveSpecialDiscountRule(SpecialDiscountAggregate specialDiscountAggregate, Long promotionId) {
        //限时折扣规则
        SpecialDiscountRuleDO specialDiscountRuleDO = CloneUtil.clone(specialDiscountAggregate.getSpecialDiscountRule(), SpecialDiscountRuleDO.class);
        BeanUtil.copyProperties(specialDiscountAggregate.getSpecialDiscountRule().getGlobalDiscountSetting(),specialDiscountRuleDO);
        specialDiscountRuleDO.setPromotionId(promotionId);
        specialDiscountRuleDAO.saveOrUpdate(specialDiscountRuleDO);
        //活动商品列表
        List<SpecialDiscountGoodsDO> specialDiscountGoodsDOS = CloneUtil.cloneList(specialDiscountAggregate.getSpecialDiscountRule().getSpecialDiscountGoods(), SpecialDiscountGoodsDO.class);
        if(CollectionUtil.isEmpty(specialDiscountGoodsDOS)){
            return;
        }
        specialDiscountGoodsDOS.forEach(specialDiscountGoodsDO -> specialDiscountGoodsDO.setPromotionId(promotionId));
        specialDiscountGoodsDAO.saveOrUpdateBatch(specialDiscountGoodsDOS);
    }
}
