package com.afiona.center.client.promotion.constants.choice;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import io.swagger.models.auth.In;

/**
 * 适用规则选择枚举（包括：适用渠道、适用商品、适用对象）
 *
 * @author dengweiyi
 * @date 2020-02-09
 */
public enum SuitChoiceEnum implements IEnum<Integer> {
    NO_SELECTED(0, "未选择", false),
    ALL(1, "所有", false),
    SUIT(2, "指定适用", true),
    NOT_SUIT(3, "指定不适用", false),

    ;

    SuitChoiceEnum(int code, String message, boolean effective) {
        this.code = code;
        this.message = message;
        this.effective = effective;
    }
    @EnumValue
    private final int code;
    private final String message;
    private final boolean effective;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public boolean isEffective() {
        return effective;
    }

    @Override
    public Integer getValue() {
        return code;
    }}
