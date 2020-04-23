package com.afiona.center.client.promotion.constants.choice;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * TODO
 *
 * @author LiJinXing
 * @date 2020/2/27
 */
public enum  MemberDiscountsTypeEnum implements IEnum<Integer> {

    /**
     * 享受会员权益
     */
    ENJOY_DISCOUNTS(0,"享受会员权益"),

    /**
     * 不享受会员权益
     */
    NO_ENJOY_DISCOUNTS(1,"不享受会员权益");

    MemberDiscountsTypeEnum(int code, String message) {
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
