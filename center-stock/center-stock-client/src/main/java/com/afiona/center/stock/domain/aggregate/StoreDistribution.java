package com.afiona.center.stock.domain.aggregate;

import cn.hutool.core.collection.CollectionUtil;
import com.afiona.center.stock.domain.model.PhysicalStock;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 店铺库存分布
 *
 * @author dengweiyi
 * @date 2020-04-11
 */
@Data
@ApiModel("店铺库存分布")
public class StoreDistribution {
    /**
     * 店铺ID
     */
    @ApiModelProperty("店铺ID")
    private long storeId;

    /**
     * 实物库存列表
     */
    private List<PhysicalStock> stocks;

    public Integer available(){
        if(CollectionUtil.isEmpty(stocks)){
            return 0;
        }
        return stocks.stream().mapToInt(PhysicalStock::getAvailable).sum();
    }

    public Integer frozen(){
        if(CollectionUtil.isEmpty(stocks)){
            return 0;
        }
        return stocks.stream().mapToInt(PhysicalStock::getFrozen).sum();
    }
}
