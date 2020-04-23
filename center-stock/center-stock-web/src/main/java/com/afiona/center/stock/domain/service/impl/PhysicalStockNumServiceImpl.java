package com.afiona.center.stock.domain.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.afiona.center.stock.constants.enums.OperateType;
import com.afiona.center.stock.constants.enums.StockFlowType;
import com.afiona.center.stock.constants.enums.result.StockResultEnum;
import com.afiona.center.stock.domain.service.PhysicalStockNumService;
import com.afiona.center.stock.infrastructure.dao.PhysicalStockDAO;
import com.afiona.center.stock.infrastructure.dao.StockFlowDAO;
import com.afiona.center.stock.infrastructure.dao.WarehouseDAO;
import com.afiona.center.stock.infrastructure.model.PhysicalStockDO;
import com.afiona.center.stock.infrastructure.model.StockFlowDO;
import com.afiona.center.stock.infrastructure.model.WarehouseDO;
import com.afiona.center.stock.model.command.PhysicalStockCommand;
import com.afiona.common.pojo.JsonResult;
import com.afiona.common.util.BatchOperateFailException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.afiona.center.stock.util.GenericHelper.*;

/**
 * 实物库存数量操作实现
 *
 * @author dengweiyi
 * @date 2020-02-25
 */
@Service
public class PhysicalStockNumServiceImpl implements PhysicalStockNumService {
    @Resource
    private PhysicalStockDAO physicalStockDAO;

    @Resource
    private WarehouseDAO warehouseDAO;

    @Resource
    private StockFlowDAO stockFlowDAO;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<Void> freeze(PhysicalStockCommand command) {
        return execute(command, "冻结库存失败", (stockParams) -> {
            PhysicalStockDO stockDO = stockParams.getStockDO();
            PhysicalStockCommand.WarehouseGoodsCount count = stockParams.getCount();
            if(stockDO == null || stockDO.getAvailable() < count.getCount()){
                return JsonResult.create(StockResultEnum.NOT_ENOUGH);
            }
            stockDO.setAvailable(stockDO.getAvailable() - count.getCount());
            stockDO.setFrozen(stockDO.getFrozen() + count.getCount());
            return JsonResult.create();
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<Void> unfreeze(PhysicalStockCommand command) {
        return execute(command, "解冻库存失败", (stockParams) -> {
            JsonResult<Void> result = reduceFrozen(stockParams);
            if(!result.success()){
                return result;
            }
            PhysicalStockDO stockDO = stockParams.getStockDO();
            PhysicalStockCommand.WarehouseGoodsCount count = stockParams.getCount();
            stockDO.setAvailable(stockDO.getAvailable() + count.getCount());
            return JsonResult.create();
        });
    }

    private JsonResult<Void> reduceFrozen(StockParams stockParams){
        PhysicalStockDO stockDO = stockParams.getStockDO();
        PhysicalStockCommand.WarehouseGoodsCount count = stockParams.getCount();
        if(stockDO == null || stockDO.getFrozen() < count.getCount()){
            return JsonResult.create(StockResultEnum.NOT_ENOUGH);
        }
        stockDO.setFrozen(stockDO.getFrozen() - count.getCount());
        return JsonResult.create();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<Void> reduce(PhysicalStockCommand command, boolean fromFrozen) {
        return execute(command, "扣减库存失败", (stockParams) -> {
            if(fromFrozen){
                return reduceFrozen(stockParams);
            }else{
                PhysicalStockDO stockDO = stockParams.getStockDO();
                PhysicalStockCommand.WarehouseGoodsCount count = stockParams.getCount();
                if(stockDO == null || stockDO.getAvailable() < count.getCount()){
                    return JsonResult.create(StockResultEnum.NOT_ENOUGH);
                }
                stockDO.setAvailable(stockDO.getAvailable() - count.getCount());
                return JsonResult.create();
            }
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<Void> increase(PhysicalStockCommand command, boolean allowedCreate) {
        return execute(command, "增加库存失败", allowedCreate, (stockParams) -> {
            PhysicalStockDO stockDO = stockParams.getStockDO();
            PhysicalStockCommand.WarehouseGoodsCount count = stockParams.getCount();
            stockDO.setAvailable(stockDO.getAvailable() + count.getCount());
            return JsonResult.create();
        });
    }

    private JsonResult<Void> execute(PhysicalStockCommand command, String errorMsg, Function<StockParams, JsonResult<Void>> function){
        return execute(command, errorMsg, false, function);
    }

    private JsonResult<Void> execute(PhysicalStockCommand command, String errorMsg, boolean allowedCreate, Function<StockParams, JsonResult<Void>> function){
        if(CollectionUtil.isEmpty(command.getCounts())){
            return JsonResult.create(StockResultEnum.EMPTY);
        }
        Set<Long> warehouseIds = command.getCounts().stream().map(PhysicalStockCommand.WarehouseGoodsCount::getWarehouseId)
                .collect(Collectors.toSet());
        List<WarehouseDO> warehouseDOList = warehouseDAO.list(new LambdaQueryWrapper<WarehouseDO>()
                .in(WarehouseDO::getId, Lists.newArrayList(warehouseIds)));
        Map<Long, WarehouseDO> warehouseDOMap = warehouseDOList.stream().collect(Collectors.toMap(WarehouseDO::getId, Function.identity()));
        List<StockFlowDO> stockFlowDOS = Lists.newArrayList();
        for(PhysicalStockCommand.WarehouseGoodsCount count : command.getCounts()){
            WarehouseDO warehouseDO = warehouseDOMap.get(count.getWarehouseId());
            if(warehouseDO == null){
                return JsonResult.create(StockResultEnum.NOT_EXIST.getCode(), "仓库不存在");
            }
            PhysicalStockDO stock = physicalStockDAO.getOne(new LambdaQueryWrapper<PhysicalStockDO>()
                    .eq(PhysicalStockDO::getWarehouseId, count.getWarehouseId())
                    .eq(PhysicalStockDO::getSkuId, count.getSkuId()));
            if(stock == null){
                if(allowedCreate){
                    stock = new PhysicalStockDO();
                    stock.setSkuId(count.getSkuId());
                    stock.setWarehouseId(count.getWarehouseId());
                    stock.setAvailable(0);
                    stock.setFrozen(0);
                    stock.setVersion(0);
                    physicalStockDAO.save(stock);
                }else{
                    throw new BatchOperateFailException(errorMsg);
                }
            }
            // 4种库存数量操作：冻结/解冻、扣减/增加
            JsonResult<Void> result = function.apply(new StockParams(stock, count));
            if(!result.success()){
                return result;
            }
            safeUpdate(stock, physicalStockDAO);
            if(command.getStockFlowType() != null && command.getStockFlowType() != StockFlowType.UN_KNOWN){
                StockFlowDO stockFlowDO = buildStockFlow(count, stock.getAvailable(), command.getStockFlowType());
                stockFlowDOS.add(stockFlowDO);
            }
        }
        if(CollectionUtil.isNotEmpty(stockFlowDOS)){
            stockFlowDAO.saveBatch(stockFlowDOS);
        }
        return JsonResult.create();
    }

    private StockFlowDO buildStockFlow(PhysicalStockCommand.WarehouseGoodsCount count, int rest, StockFlowType stockFlowType){
        StockFlowDO stockFlowDO = new StockFlowDO();
        stockFlowDO.setWarehouseId(count.getWarehouseId());
        stockFlowDO.setSkuId(count.getSkuId());
        stockFlowDO.setNum(count.getCount());
        stockFlowDO.setRest(rest);
        stockFlowDO.setOperateType(stockFlowType.isIncrease() ? OperateType.INCREASE : OperateType.DECREASE);
        stockFlowDO.setFlowType(stockFlowType);
        return stockFlowDO;
    }

    @Data
    @AllArgsConstructor
    private static class StockParams{
        private PhysicalStockDO stockDO;
        private PhysicalStockCommand.WarehouseGoodsCount count;
    }
}
