package com.afiona.center.client.promotion.constants.choice;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 折扣类型选择
 *
 * @author LiJinXing
 * @date 2020/2/23
 */
public enum  DiscountChoiceEnum implements IEnum<Integer> {

    /**
     * 减X元
     */
    AMOUNT(0,"减x元"),

    /**
     * 打x折
     */
    DISCOUNT(1,"打x折"),

    /**
     * 折后
     */
    AFTER_DISCOUNT(2,"折后"),
    ;
    @EnumValue
    private int code;

    private String message;

    DiscountChoiceEnum(int code, String message) {
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
