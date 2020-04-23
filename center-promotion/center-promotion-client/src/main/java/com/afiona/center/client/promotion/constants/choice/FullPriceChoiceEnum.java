package com.afiona.center.client.promotion.constants.choice;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * 满x元条件选择枚举
 *
 * @author dengweiyi
 * @date 2020-02-09
 */
public enum  FullPriceChoiceEnum implements IEnum<Integer> {
    PRICE(0, "按照正价计算"),
    REAL(1, "按照实付金额计算"),

    ;

    FullPriceChoiceEnum(int code, String message) {
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
