package com.afiona.center.stock.constants.enums;
/**
 * 出库类型枚举
 *
 * @author dengweiyi
 * @date 2020-02-27
 */
public enum  OutStockType {
    /**
     * 销售出库
     */
    SALE(0, "销售出库", StockFlowType.SALE_OUT),

    /**
     * 调拨出库
     */
    TRANSFER(1, "调拨出库", StockFlowType.SALE_OUT),

    /**
     * 调整出库
     */
    ADJUST(2, "调整出库", StockFlowType.SALE_OUT),

    /**
     * 盘点出库
     */
    INVENTORY(3, "盘点出库", StockFlowType.SALE_OUT),

    ;

    OutStockType(int code, String msg, StockFlowType flowType) {
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
