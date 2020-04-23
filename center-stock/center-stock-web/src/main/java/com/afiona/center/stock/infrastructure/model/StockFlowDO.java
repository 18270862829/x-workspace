package com.afiona.center.stock.infrastructure.model;

import com.afiona.center.stock.constants.enums.OperateType;
import com.afiona.center.stock.constants.enums.StockFlowType;
import com.afiona.common.model.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 库存流水DO
 *
 * @author dengweiyi
 * @date 2020-02-27
 */
@Data
@TableName(value = "stock_flow")
public class StockFlowDO extends SuperEntity {
    /**
     * SKU ID
     */
    private Long skuId;

    /**
     * 仓库ID
     */
    private Long warehouseId;

    /**
     * 单据编码
     */
    private String code;

    /**
     * 关联单号
     */
    private String relatedCode;

    /**
     * 单据类型
     */
    private StockFlowType flowType;

    /**
     * 操作类型
     */
    private OperateType operateType;

    /**
     * 库存改变量
     */
    private Integer num;

    /**
     * 库存余量
     */
    private Integer rest;
}
