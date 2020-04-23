package com.afiona.center.stock.model.query;

import com.afiona.center.stock.constants.enums.WarehouseStatus;
import com.afiona.center.stock.constants.enums.WarehouseType;
import com.afiona.common.pojo.PageQuery;
import lombok.Data;

/**
 * 仓库查询
 *
 * @author dengweiyi
 * @date 2020-02-25
 */
@Data
public class WarehouseQuery extends PageQuery {
    /**
     * 仓库名称
     */
    private String name;

    /**
     * 仓库状态
     */
    private WarehouseStatus status;

    /**
     * 仓库类型
     */
    private WarehouseType type;
}
