package com.afiona.center.stock.infrastructure.repo.impl;

import com.afiona.center.stock.constants.enums.StockFlowType;
import com.afiona.center.stock.domain.aggregate.StockFlowAggregate;
import com.afiona.center.stock.domain.repo.StockFlowRepository;
import com.afiona.center.stock.domain.service.aggregate.StockFlowAggregateService;
import com.afiona.center.stock.infrastructure.dao.StockFlowDAO;
import com.afiona.center.stock.infrastructure.model.StockFlowDO;
import com.afiona.center.stock.model.query.StockFlowQuery;
import com.afiona.common.util.CloneUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.deepexi.util.pageHelper.PageBean;
import com.deepexi.util.pojo.ObjectCloneUtils;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

import cn.hutool.core.collection.CollUtil;

/**
 * 库存流水repo实现
 *
 * @author dengweiyi
 * @date 2020-02-27
 */
@Repository
public class StockFlowRepositoryImpl implements StockFlowRepository {
    @Resource
    private StockFlowDAO stockFlowDAO;

    @Resource
    private StockFlowAggregateService stockFlowAggregateService;

    @Override
    public PageBean<StockFlowAggregate> listPage(StockFlowQuery query) {
        PageHelper.startPage(query.getPage(), query.getSize());
        boolean hasRange = query.getRange() != null;
        List<StockFlowType> stockFlowTypeList = StockFlowType.findByType(query.getIncrease());
        List<StockFlowDO> stockFlowDOS = stockFlowDAO.list(new LambdaQueryWrapper<StockFlowDO>()
                .eq(query.getSkuId() != null, StockFlowDO::getSkuId, query.getSkuId())
                .eq(query.getFlowType() != null, StockFlowDO::getFlowType, query.getFlowType())
                .in(CollUtil.isNotEmpty(stockFlowTypeList), StockFlowDO::getFlowType, stockFlowTypeList)
                .eq(query.getCode() != null, StockFlowDO::getCode, query.getCode())
                .eq(query.getRelatedCode() != null, StockFlowDO::getRelatedCode, query.getRelatedCode())
                .eq(query.getCreatedBy() != null, StockFlowDO::getCreatedBy, query.getCreatedBy())
                .between(hasRange, StockFlowDO::getCreatedTime, hasRange ? query.getRange().getMin() : null,
                        hasRange ? query.getRange().getMax() : null)
                .eq(query.getWarehouseId() != null, StockFlowDO::getWarehouseId, query.getWarehouseId())
                .orderByDesc(StockFlowDO::getCreatedTime));
        // TODO 缺失：仓库/门店过滤
        List<StockFlowAggregate> stockFlows = ObjectCloneUtils.convertList(stockFlowDOS, StockFlowAggregate.class);
        stockFlowAggregateService.aggregate(stockFlows);

        PageBean<StockFlowDO> oldPageBean = new PageBean<>(stockFlowDOS);
        PageBean<StockFlowAggregate> pageBean = CloneUtil.clone(oldPageBean, PageBean.class);
        pageBean.setContent(stockFlows);
        return pageBean;
    }
}
