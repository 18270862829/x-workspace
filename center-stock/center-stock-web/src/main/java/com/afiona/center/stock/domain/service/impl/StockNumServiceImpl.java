package com.afiona.center.stock.domain.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.afiona.center.stock.constants.enums.result.StockResultEnum;
import com.afiona.center.stock.domain.repo.PhysicalStockRepository;
import com.afiona.center.stock.infrastructure.model.StockDO;
import com.afiona.center.stock.model.IdAndCount;
import com.afiona.center.stock.model.command.StockCommand;
import com.afiona.center.stock.domain.service.StockNumService;
import com.afiona.center.stock.infrastructure.dao.StockDAO;
import com.afiona.common.pojo.JsonResult;
import com.afiona.common.util.BatchOperateFailException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.afiona.center.stock.util.GenericHelper.*;

/**
 * 销售库存数量操作实现
 *
 * @author dengweiyi
 * @date 2020-01-06
 */
@Service
public class StockNumServiceImpl implements StockNumService {
    @Resource
    private StockDAO stockDAO;

    @Resource
    private PhysicalStockRepository physicalStockRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<Void> freeze(StockCommand command) {
        return execute(command, "冻结库存失败", (stockParams) -> {
            StockDO stockDO = stockParams.stockDO;
            IdAndCount idAndCount = stockParams.idAndCount;
            if(stockDO == null || stockDO.getAvailable() < idAndCount.getCount()){
                return JsonResult.create(StockResultEnum.NOT_ENOUGH);
            }
            stockDO.setAvailable(stockDO.getAvailable() - idAndCount.getCount());
            stockDO.setFrozen(stockDO.getFrozen() + idAndCount.getCount());
            return JsonResult.create();
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<Void> unfreeze(StockCommand command) {
        return execute(command, "解冻库存失败", (stockParams) -> {
            JsonResult<Void> result = reduceFrozen(stockParams);
            if(!result.success()){
                return result;
            }
            StockDO stockDO = stockParams.stockDO;
            IdAndCount idAndCount = stockParams.idAndCount;
            stockDO.setAvailable(stockDO.getAvailable() + idAndCount.getCount());
            return JsonResult.create();
        });
    }

    private JsonResult<Void> reduceFrozen(StockParams stockParams){
        StockDO stockDO = stockParams.stockDO;
        IdAndCount idAndCount = stockParams.idAndCount;
        if(stockDO == null || stockDO.getFrozen() < idAndCount.getCount()){
            return JsonResult.create(StockResultEnum.NOT_ENOUGH);
        }
        stockDO.setFrozen(stockDO.getFrozen() - idAndCount.getCount());
        return JsonResult.create();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<Void> reduce(StockCommand command, boolean fromFrozen) {
        return execute(command, "扣减库存失败", (stockParams) -> reduceFrozen(stockParams));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<Void> increase(StockCommand command, boolean allowedCreate) {
        return execute(command, "增加库存失败", allowedCreate, (stockParams) -> {
            StockDO stockDO = stockParams.stockDO;
            IdAndCount idAndCount = stockParams.idAndCount;
            stockDO.setFrozen(stockDO.getAvailable() + idAndCount.getCount());
            return JsonResult.create();
        });
    }

    private JsonResult<Void> execute(StockCommand command, String errorMsg, Function<StockParams, JsonResult<Void>> function){
        return execute(command, errorMsg, false, function);
    }

    private JsonResult<Void> execute(StockCommand command, String errorMsg, boolean allowedCreate, Function<StockParams, JsonResult<Void>> function){
        List<IdAndCount> idAndCountList = command.getIdAndCountList();
        if(CollectionUtil.isEmpty(idAndCountList)){
            return JsonResult.create(StockResultEnum.EMPTY);
        }
        List<Long> idList = command.getIdAndCountList().stream().map(IdAndCount::getId).collect(Collectors.toList());
        List<StockDO> stocks = stockDAO.list(new LambdaQueryWrapper<StockDO>()
                .eq(StockDO::getStoreId, command.getStoreId())
                .in(StockDO::getSkuId, idList));
        Map<Long, StockDO> stockMap = stocks.stream().collect(Collectors.toMap(StockDO::getSkuId, Function.identity()));
        for(IdAndCount idAndCount : idAndCountList){
            if(idAndCount.getCount() <= 0){
                return JsonResult.create(StockResultEnum.PARAM_ERROR);
            }
            StockDO stock = stockMap.get(idAndCount.getId());
            if(stock == null){
                if(allowedCreate){
                    stock = new StockDO();
                    stock.setSkuId(idAndCount.getId());
                    stock.setStoreId(command.getStoreId());
                    stock.setAvailable(idAndCount.getCount());
                    stock.setFrozen(0);
                    stockDAO.save(stock);
                    continue;
                }else{
                    throw new BatchOperateFailException(errorMsg);
                }
            }
            // 4种库存数量操作：冻结/解冻、扣减/增加
            JsonResult<Void> result = function.apply(new StockParams(stock, idAndCount));
            if(!result.success()){
                return result;
            }
            safeUpdate(stock, stockDAO);
        }
        return JsonResult.create();
    }

    @Data
    @AllArgsConstructor
    private static class StockParams{
        private StockDO stockDO;
        private IdAndCount idAndCount;
    }
}
