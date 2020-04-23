package com.afiona.center.web.promotion.domain.service.resolve.impl;

import com.afiona.center.client.promotion.constants.PromotionResultEnum;
import com.afiona.center.client.promotion.domain.aggregate.AddMoneyToBuyAggregate;
import com.afiona.center.web.promotion.domain.service.resolve.IBaseResolve;
import com.afiona.center.web.promotion.domain.service.resolve.IResolve;
import com.afiona.center.web.promotion.infrastructure.dao.AddMoneyToBuyGoodsDAO;
import com.afiona.center.web.promotion.infrastructure.dao.AddMoneyToBuyRuleDAO;
import com.afiona.center.web.promotion.infrastructure.model.AddMoneyToBuyGoodsDO;
import com.afiona.center.web.promotion.infrastructure.model.AddMoneyToBuyRuleDO;
import com.afiona.common.pojo.JsonResult;
import com.afiona.common.util.CloneUtil;
import com.deepexi.util.CollectionUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 加价购解析器
 *
 * @author LiJinXing
 * @date 2020/2/22
 */
@Service
public class AddMoneyToBuyResolveService implements IResolve<AddMoneyToBuyAggregate> {

    @Resource
    private IBaseResolve<AddMoneyToBuyAggregate> baseResolveService;

    @Resource
    private AddMoneyToBuyRuleDAO addMoneyToBuyRuleDAO;

    @Resource
    private AddMoneyToBuyGoodsDAO addMoneyToBuyGoodsDAO;

    @Override
    public Long resolve(AddMoneyToBuyAggregate addMoneyToBuyAggregate) {
        Long promotionId = baseResolveService.resolve(addMoneyToBuyAggregate);
        if(promotionId==null){
            JsonResult.create(PromotionResultEnum.DATA_ERROR);
        }
        resolveAddMoneyToBuyRule(addMoneyToBuyAggregate,promotionId);
        return promotionId;
    }

    private void resolveAddMoneyToBuyRule(AddMoneyToBuyAggregate addMoneyToBuyAggregate, Long promotionId) {
        //加价购规则
        AddMoneyToBuyRuleDO addMoneyToBuyRuleDO = CloneUtil.clone(addMoneyToBuyAggregate.getAddMoneyToBuyRule(), AddMoneyToBuyRuleDO.class);
        addMoneyToBuyRuleDO.setPromotionId(promotionId);
        addMoneyToBuyRuleDAO.saveOrUpdate(addMoneyToBuyRuleDO);
        //换购商品
        List<AddMoneyToBuyGoodsDO> addMoneyToBuyGoodsDOList = CloneUtil.cloneList(addMoneyToBuyAggregate.getAddMoneyToBuyRule().getAddMoneyToBuyGoodsList(), AddMoneyToBuyGoodsDO.class);
        if(CollectionUtil.isEmpty(addMoneyToBuyGoodsDOList)){
            return;
        }
        for (AddMoneyToBuyGoodsDO addMoneyToBuyGoodsDO : addMoneyToBuyGoodsDOList) {
            addMoneyToBuyGoodsDO.setPromotionId(promotionId);
            addMoneyToBuyGoodsDO.setId(null);
        }

        addMoneyToBuyGoodsDAO.saveOrUpdateBatch(addMoneyToBuyGoodsDOList);
    }


}
