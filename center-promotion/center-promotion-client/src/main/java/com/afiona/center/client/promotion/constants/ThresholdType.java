package com.afiona.center.client.promotion.constants;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author CLB
 * @Description 门槛类型
 * @Date 2020/1/2 16:25
 */
public enum ThresholdType implements IEnum<Integer> {

    /**
     * 无门槛
     */
    ALL(0, "无门槛"),

    /**
     * 金额门槛
     */
    AMOUNT(1, "金额门槛"),

    /**
     * 单件门槛
     */
    SINGLETON(2, "单件门槛"),
    ;
    @EnumValue
    private int code;

    private String message;

    ThresholdType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 获取code
     *
     * @return code
     */
    public int getCode() {
        return code;
    }

    /**
     * 获取信息
     *
     * @return 信息
     */
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getValue() {
        return code;
    }
}
