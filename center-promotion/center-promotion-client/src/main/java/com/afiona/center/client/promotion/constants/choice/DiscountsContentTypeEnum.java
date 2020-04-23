package com.afiona.center.client.promotion.constants.choice;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 优惠内容类型
 *
 * @author LiJinXing
 * @date 2020/2/25
 */
public enum  DiscountsContentTypeEnum implements IEnum<Integer> {

    /**
     * 赠送积分
     */
    GIFT_INTEGRAL(0,"赠送积分"),

    /**
     * 赠送优惠券
     */
    GIFT_COUNPON(1,"赠送优惠券"),

    /**
     * 赠送礼品
     */
    GIFT(2,"赠送礼品");
    @EnumValue
    private int code;

    private String message;

    DiscountsContentTypeEnum(int code, String message) {
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
