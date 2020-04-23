package com.afiona.center.client.promotion.constants.choice;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 支付有礼优惠类型
 *
 * @author LiJinXing
 * @date 2020/2/25
 */
public enum  PayGiftTypeEnum implements IEnum<Integer> {

    /**
     * 阶梯有礼
     */
    STAIR(0, "阶梯有礼"),

    /**
     * 循环有礼
     */
    LOOP(1, "循环有礼"),
            ;
    @EnumValue
    private int code;

    private String message;

    PayGiftTypeEnum(int code, String message) {
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
