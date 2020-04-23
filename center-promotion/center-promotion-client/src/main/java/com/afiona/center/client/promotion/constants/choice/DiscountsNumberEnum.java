package com.afiona.center.client.promotion.constants.choice;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 *优惠数量
 *
 * @author LiJinXing
 * @date 2020/2/22
 */
public enum DiscountsNumberEnum implements IEnum<Integer> {

    /**
     * 可换购多件
     */
    TO_ONE(0,"可换购一件"),

    /**
     * 可换购多件
     */
    T0_MANY(1,"可换购多件");
    @EnumValue
    private int code;

    private String message;

    DiscountsNumberEnum(int code, String message) {
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
