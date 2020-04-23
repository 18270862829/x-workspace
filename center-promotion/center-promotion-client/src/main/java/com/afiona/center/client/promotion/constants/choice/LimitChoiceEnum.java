package com.afiona.center.client.promotion.constants.choice;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * 商品限制枚举
 *
 * @author dengweiyi
 * @date 2020-02-09
 */
public enum  LimitChoiceEnum implements IEnum<Integer> {

    /**
     * 可重复选购
     */
    REPEAT_ALLOWED(0, "可重复选购"),

    /**
     * 不可重复选购
     */
    REPEAT_NOT_ALLOWED(1, "不可重复选购");

    LimitChoiceEnum(int code, String message) {
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
    }
}
