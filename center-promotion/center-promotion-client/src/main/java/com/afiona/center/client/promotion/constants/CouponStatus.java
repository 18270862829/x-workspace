package com.afiona.center.client.promotion.constants;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author CLB
 * @Description 优惠券状态
 * @Date 2020/1/2 16:25
 */
public enum CouponStatus implements IEnum<Integer> {

    /**
     * 未开始
     */
    HAS_NOT_STARTED(0, "未开始"),

    /**
     * 进行中
     */
    PROCESSING(1, "进行中"),

    /**
     * 已过期
     */
    EXPIRED(2, "已过期"),
    ;
    @EnumValue
    private int code;

    private String message;

    CouponStatus(int code, String message) {
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
