package com.afiona.center.stock.constants.enums;
/**
 * 操作类型
 *
 * @author dengweiyi
 * @date 2020-02-25
 */
public enum OperateType {
    DECREASE(0, "扣减"),
    INCREASE(1, "增加"),

    ;

    OperateType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private final int code;
    private final String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
