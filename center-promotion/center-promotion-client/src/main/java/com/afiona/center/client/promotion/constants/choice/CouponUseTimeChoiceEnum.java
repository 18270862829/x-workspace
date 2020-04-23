package com.afiona.center.client.promotion.constants.choice;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * 优惠劵适用时间选择枚举
 *
 * @author dengweiyi
 * @date 2020-02-09
 */
public enum  CouponUseTimeChoiceEnum implements IEnum<Integer> {
    RANGE(0, "开始时间:结束时间"),
    THE_SAM_DAY(1, "从领劵日起x天可用"),
    THE_NEXT_DAY(2, "领劵次日起x天可用"),
    ;

    CouponUseTimeChoiceEnum(int code, String message) {
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
