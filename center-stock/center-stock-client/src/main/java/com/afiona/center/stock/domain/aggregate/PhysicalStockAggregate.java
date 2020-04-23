package com.afiona.center.stock.domain.aggregate;

import com.afiona.center.stock.domain.model.PhysicalStock;
import com.afiona.center.stock.domain.model.Warehouse;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 实物库存聚合根
 *
 * @author dengweiyi
 * @date 2020-02-25
 */
@Data
@ApiModel("实物库存聚合根")
public class PhysicalStockAggregate extends PhysicalStock {
    /**
     * 仓库
     */
    private Warehouse warehouse;
}
