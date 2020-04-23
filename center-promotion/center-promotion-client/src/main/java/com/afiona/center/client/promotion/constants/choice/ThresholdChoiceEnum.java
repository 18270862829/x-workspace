package com.afiona.center.client.promotion.constants.choice;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * 优惠门槛选择枚举
 *
 * @author dengweiyi
 * @date 2020-02-09
 */
public enum ThresholdChoiceEnum implements IEnum<Integer> {
    AMOUNT(0, "满元"),
    NUM(1, "满件"),

    ;

    ThresholdChoiceEnum(int code, String message) {
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
