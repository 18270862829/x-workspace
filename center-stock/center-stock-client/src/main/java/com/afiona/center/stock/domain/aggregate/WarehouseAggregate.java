package com.afiona.center.stock.domain.aggregate;

import cn.hutool.core.collection.CollectionUtil;
import com.afiona.center.stock.domain.model.PhysicalStock;
import com.afiona.center.stock.domain.model.Warehouse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 仓库聚合根
 *
 * @author dengweiyi
 * @date 2020-02-25
 */
@Data
@ApiModel("仓库聚合根")
public class WarehouseAggregate extends Warehouse {
    /**
     * 店铺ID列表
     */
    @ApiModelProperty("店铺ID列表")
    private List<Long> storeIds;

    /**
     * 实物库存列表
     *
     * @param null
     * @return : null
     */
    private List<PhysicalStock> stocks;

    public String getFriendlyAddress(){
        if(getAddress() == null || getAddress().getRegin() == null){
            return "";
        }
        return String.format("%s%s%s%s", getAddress().getRegin().getProvince(), getAddress().getRegin().getCity(),
                getAddress().getRegin().getArea(), getAddress().getDetailedAddress());
    }

    public int available(){
        if(CollectionUtil.isEmpty(stocks)){
            return 0;
        }
        return stocks.stream().mapToInt(PhysicalStock::getAvailable).sum();
    }

    public int frozen(){
        if(CollectionUtil.isEmpty(stocks)){
            return 0;
        }
        return stocks.stream().mapToInt(PhysicalStock::getFrozen).sum();
    }
}
