package com.afiona.center.client.promotion.constants;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * 优惠类型枚举
 *
 * @author dengweiyi
 * @date 2020-02-09
 */
public enum PreferentialType implements IEnum<Integer> {
    STAIR(0, "阶梯满减"),
    LOOP(1, "循环满减"),
    ;

    PreferentialType(int code, String message) {
        this.code = code;
        this.message = message;
    }
    @EnumValue
    private final int code;
    private final String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


    @Override
    public Integer getValue() {
        return code;
    }}
