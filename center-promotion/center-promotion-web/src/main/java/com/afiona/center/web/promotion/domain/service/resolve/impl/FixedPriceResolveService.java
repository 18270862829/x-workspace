package com.afiona.center.web.promotion.domain.service.resolve.impl;

import com.afiona.center.client.promotion.constants.PromotionResultEnum;
import com.afiona.center.client.promotion.domain.aggregate.AddMoneyToBuyAggregate;
import com.afiona.center.client.promotion.domain.aggregate.FixedPriceAggregate;
import com.afiona.center.web.promotion.domain.service.resolve.IBaseResolve;
import com.afiona.center.web.promotion.domain.service.resolve.IResolve;
import com.afiona.center.web.promotion.infrastructure.dao.FixedPriceGoodsDAO;
import com.afiona.center.web.promotion.infrastructure.dao.FixedPriceRuleDAO;
import com.afiona.center.web.promotion.infrastructure.model.FixedPriceGoodsDO;
import com.afiona.center.web.promotion.infrastructure.model.FixedPriceRuleDO;
import com.afiona.common.pojo.JsonResult;
import com.afiona.common.util.CloneUtil;
import com.deepexi.util.CollectionUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * 促销一口价解析器
 *
 * @author LiJinXing
 * @date 2020/2/13
 */
@Service
public class FixedPriceResolveService  implements IResolve<FixedPriceAggregate> {

    @Resource
    private IBaseResolve<FixedPriceAggregate> baseResolveService;

    @Resource
    private FixedPriceRuleDAO fixedPriceRuleDAO;

    @Resource
    private FixedPriceGoodsDAO fixedPriceGoodsDAO;

    @Override
    public Long resolve(FixedPriceAggregate fixedPriceAggregate){
        Long promotionId = baseResolveService.resolve(fixedPriceAggregate);
        if(promotionId==null){
            JsonResult.create(PromotionResultEnum.DATA_ERROR);
        }
        resolveFixedPriceRule(fixedPriceAggregate,promotionId);
        return promotionId;
    }

    private void resolveFixedPriceRule(FixedPriceAggregate fixedPriceAggregate,Long promotionId) {
        FixedPriceRuleDO fixedPriceRuleDO = CloneUtil.clone(fixedPriceAggregate.getFixedPriceRule(), FixedPriceRuleDO.class);
        fixedPriceRuleDO.setPromotionId(promotionId);
        fixedPriceRuleDAO.saveOrUpdate(fixedPriceRuleDO);
        List<FixedPriceGoodsDO> fixedPriceGoodsDOS = CloneUtil.cloneList(fixedPriceAggregate.getFixedPriceRule().getFixedPriceGoods(),
                FixedPriceGoodsDO.class);
        if(CollectionUtil.isEmpty(fixedPriceGoodsDOS)){
            return;
        }
        fixedPriceGoodsDOS.forEach(fixedPriceGoodsDO -> fixedPriceGoodsDO.setPromotionId(promotionId));

        fixedPriceGoodsDAO.saveOrUpdateBatch(fixedPriceGoodsDOS);
    }
}
