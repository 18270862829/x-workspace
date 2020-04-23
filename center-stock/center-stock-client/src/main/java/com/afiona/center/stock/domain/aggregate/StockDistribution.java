package com.afiona.center.stock.domain.aggregate;

import cn.hutool.core.collection.CollectionUtil;
import com.deepexi.util.pojo.AbstractObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 商品的库存分布
 *
 * @author dengweiyi
 * @date 2020-02-25
 */
@Data
@ApiModel("库存分布")
public class StockDistribution extends AbstractObject {
    /**
     * SKU ID
     */
    @ApiModelProperty("SKU ID")
    private long skuId;

    /**
     * 实物库存列表
     */
    private List<PhysicalStockAggregate> stocks;

    public int sumAvailable(){
        if(CollectionUtil.isEmpty(stocks)){
            return 0;
        }
        return stocks.stream().mapToInt(PhysicalStockAggregate::getAvailable).sum();
    }

    public int sumFrozen(){
        if(CollectionUtil.isEmpty(stocks)){
            return 0;
        }
        return stocks.stream().mapToInt(PhysicalStockAggregate::getFrozen).sum();
    }
}
