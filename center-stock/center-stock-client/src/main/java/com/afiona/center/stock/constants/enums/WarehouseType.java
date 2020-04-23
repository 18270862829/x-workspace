package com.afiona.center.stock.constants.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * 仓库类型
 *
 * @author dengweiyi
 * @date 2020-02-24
 */
public enum WarehouseType implements IEnum<Integer>{
    /**
     * 实物仓
     */
    REAL(0, "实物仓"),

    /**
     * 虚拟仓
     */
    VIRTUAL(1, "虚拟仓")

    ;

    WarehouseType(int code, String msg) {
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
    }}
