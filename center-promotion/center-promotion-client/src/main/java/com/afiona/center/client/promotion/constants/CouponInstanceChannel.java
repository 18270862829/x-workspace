package com.afiona.center.client.promotion.constants;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author CLB
 * @Description 优惠券获取途径
 * @Date 2020/1/2 16:25
 */
public enum CouponInstanceChannel implements IEnum<Integer> {

    /**
     * 活动领取
     */
    CAMPAIGN(0, "活动获取"),

    /**
     * 主动获取
     */
    INDEPENDENT_TO_RECEIVE(1, "主动获取"),
    ;
    @EnumValue
    private int code;

    private String message;

    CouponInstanceChannel(int code, String message) {
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
