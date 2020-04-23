package com.afiona.center.stock.infrastructure.model;

import com.afiona.common.model.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 仓库渠道关联关系DO
 *
 * @author dengweiyi
 * @date 2020-02-25
 */
@Data
@TableName(value = "warehouse_store")
public class WarehouseStoreDO extends SuperEntity {
    /**
     * 仓库ID
     */
    private Long warehouseId;

    /**
     * 店铺ID
     */
    private Long storeId;

    /**
     * 供货优先级
     */
    private Integer priority;
}
