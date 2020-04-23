package com.afiona.center.client.promotion.constants;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 套装类型
 *
 * @author LiJinXing
 * @date 2020/2/19
 */
public enum SuitType implements IEnum<Integer> {

    /**
     * 固定套装
     */
    FIXED_SUIT(0,"固定套装");

    @EnumValue
    private int code;

    private String message;

    SuitType(int code, String message) {
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
