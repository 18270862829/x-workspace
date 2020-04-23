package com.afiona.center.client.promotion.constants.choice;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * 叠加类型
 *
 * @author LiJinXing
 * @date 2020/2/27
 */
public enum  SuperimposedTypeEnum implements IEnum<Integer> {

    /**
     * 与所有活动叠加
     */
    ALL_SUPERIMPOSED(0,"与所有活动叠加"),

    /**
     * 与所有活动不叠加
     */
    NO_SUPERIMPOSED(1,"与所有活动互斥"),

    /**
     * 与指定活动互斥
     */
    SECTIONAL_SUPERIMPOSED(2,"与指定活动互斥");

    SuperimposedTypeEnum(int code, String message) {
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
