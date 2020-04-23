package com.afiona.center.stock.constants.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * 仓库状态
 *
 * @author dengweiyi
 * @date 2020-02-24
 */
public enum WarehouseStatus implements IEnum<Integer> {
    /**
     * 禁用
     */
    TURN_OFF(0, "禁用"),

    /**
     * 启用
     */
    TURN_ON(1, "启用")

    ;

    WarehouseStatus(int code, String msg) {
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

    @Override
    public Integer getValue() {
        return code;
    }
}