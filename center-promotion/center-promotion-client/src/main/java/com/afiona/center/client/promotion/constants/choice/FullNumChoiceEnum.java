package com.afiona.center.client.promotion.constants.choice;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * 满x件条件选择枚举
 *
 * @author dengweiyi
 * @date 2020-02-09
 */
public enum FullNumChoiceEnum implements IEnum<Integer> {
    ALL(0, "所有商品"),
    DIFFERENT(1, "仅不同商品"),
    SAME(2, "仅相同商品")

    ;

    FullNumChoiceEnum(int code, String message) {
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
