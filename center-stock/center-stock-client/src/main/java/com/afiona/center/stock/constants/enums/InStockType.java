package com.afiona.center.stock.constants.enums;
/**
 * 入库类型枚举
 *
 * @author dengweiyi
 * @date 2020-02-27
 */
public enum InStockType {
    /**
     * 采购入库
     */
    PURCHASE(0, "采购入库", StockFlowType.PURCHASE_IN),

    /**
     * 退货入库
     */
    REFUND(1, "退货入库", StockFlowType.PURCHASE_IN),

    /**
     * 调拨入库
     */
    TRANSFER(2, "调拨入库", StockFlowType.PURCHASE_IN),

    /**
     * 调整入库
     */
    ADJUST(3, "调整入库", StockFlowType.PURCHASE_IN),

    /**
     * 盘点入库
     */
    INVENTORY(4, "盘点入库", StockFlowType.PURCHASE_IN),

    ;

    InStockType(int code, String msg, StockFlowType flowType) {
        this.code = code;
        this.msg = msg;
        this.flowType = flowType;
    }

    private final int code;
    private final String msg;
    private final StockFlowType flowType;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public StockFlowType getFlowType() {
        return flowType;
    }
}
