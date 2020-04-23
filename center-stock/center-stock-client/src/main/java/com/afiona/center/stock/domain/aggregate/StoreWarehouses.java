package com.afiona.center.stock.domain.aggregate;

import com.afiona.center.stock.domain.model.Warehouse;
import lombok.Data;

import java.util.List;

/**
 * 店铺仓库列表
 *
 * @author dengweiyi
 * @date 2020-03-19
 */

@Data
public class StoreWarehouses {
    /**
     * 店铺ID
     */
    private long storeId;

    /**
     * 仓库列表
     */
    private List<Warehouse> warehouses;
}
