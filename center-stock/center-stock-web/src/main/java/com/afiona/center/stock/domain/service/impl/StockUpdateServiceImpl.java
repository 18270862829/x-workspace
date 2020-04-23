package com.afiona.center.stock.domain.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.afiona.center.stock.domain.service.StockUpdateService;
import com.afiona.center.stock.infrastructure.dao.PhysicalStockDAO;
import com.afiona.center.stock.infrastructure.dao.StockDAO;
import com.afiona.center.stock.infrastructure.dao.WarehouseStoreDAO;
import com.afiona.center.stock.infrastructure.model.PhysicalStockDO;
import com.afiona.center.stock.infrastructure.model.StockDO;
import com.afiona.center.stock.infrastructure.model.WarehouseStoreDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.afiona.center.stock.util.GenericHelper.*;

/**
 * 销售库存更新操作实现
 *
 * @author dengweiyi
 * @date 2020-02-26
 */
@Service
public class StockUpdateServiceImpl implements StockUpdateService {
    @Resource
    private WarehouseStoreDAO warehouseStoreDAO;

    @Resource
    private PhysicalStockDAO physicalStockDAO;

    @Resource
    private StockDAO stockDAO;

    @Override
    public void update() {
        // 查询全量数据，暂未考虑性能
        List<WarehouseStoreDO> allWarehouseChannels = warehouseStoreDAO.list();
        Map<Long, List<WarehouseStoreDO>> warehouseChannelMap = allWarehouseChannels.stream()
                .collect(Collectors.groupingBy(WarehouseStoreDO::getStoreId));
        List<PhysicalStockDO> allPhysicalStocks = physicalStockDAO.list();
        Map<Long, List<PhysicalStockDO>> physicalStockMap = allPhysicalStocks.stream()
                .collect(Collectors.groupingBy(PhysicalStockDO::getWarehouseId));

        for(Map.Entry<Long, List<WarehouseStoreDO>> entry : warehouseChannelMap.entrySet()){
            long storeId = entry.getKey();
            List<WarehouseStoreDO> warehouseStoreDOList = entry.getValue();

            // 汇总本渠道下所有实物库存
            List<PhysicalStockDO> channelPhysicalStocks = Lists.newArrayList();
            for(WarehouseStoreDO warehouseStoreDO : warehouseStoreDOList){
                List<PhysicalStockDO> physicalStockDOList = physicalStockMap.get(warehouseStoreDO.getWarehouseId());
                if(CollectionUtil.isEmpty(physicalStockDOList)){
                    continue;
                }
                channelPhysicalStocks.addAll(physicalStockDOList);
            }

            Map<Long, List<PhysicalStockDO>> skuPhysicalStockMap = channelPhysicalStocks.stream()
                    .collect(Collectors.groupingBy(PhysicalStockDO::getSkuId));
            for(Map.Entry<Long, List<PhysicalStockDO>> skuEntry : skuPhysicalStockMap.entrySet()){
                long skuId = skuEntry.getKey();
                List<PhysicalStockDO> skuPhysicalStocks = skuEntry.getValue();
                int sum = skuPhysicalStocks.stream().mapToInt(PhysicalStockDO::getAvailable).sum();

                // 更新销售库存
                StockDO stock = stockDAO.getOne(new LambdaQueryWrapper<StockDO>()
                        .eq(StockDO::getStoreId, storeId)
                        .eq(StockDO::getSkuId, skuId));
                if(stock == null){
                    stock = new StockDO();
                    stock.setSkuId(skuId);
                    stock.setStoreId(storeId);
                    stock.setAvailable(sum);
                    stock.setFrozen(0);
                    stockDAO.save(stock);
                }else{
                    stock.setAvailable(sum);
                    safeUpdate(stock, stockDAO);
                }
            }
        }
    }
}
