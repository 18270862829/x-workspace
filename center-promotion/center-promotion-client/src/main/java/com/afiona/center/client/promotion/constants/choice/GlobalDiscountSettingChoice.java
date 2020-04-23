package com.afiona.center.client.promotion.constants.choice;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * 全局折扣设置选择枚举
 *
 * @author dengweiyi
 * @date 2020-02-09
 */
public enum  GlobalDiscountSettingChoice implements IEnum<Integer> {
    /**
     * 减x元
     */
    AMOUNT(0, "减x元"),
    /**
     * 折扣
     */
    DISCOUNT(1, "折扣"),

    ;

    GlobalDiscountSettingChoice(int code, String message) {
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
