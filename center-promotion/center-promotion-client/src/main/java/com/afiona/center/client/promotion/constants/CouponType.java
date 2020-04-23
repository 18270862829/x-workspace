package com.afiona.center.client.promotion.constants;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author CLB
 * @Description 优惠券类型
 * @Date 2020/1/2 16:25
 */
public enum CouponType implements IEnum<Integer> {

    /**
     * 代金券
     */
    VOUCHER(0, "代金券"),

    /**
     * 折扣券
     */
    DISCOUNT(1, "折扣券"),
    ;
    @EnumValue
    private int code;

    private String message;

    CouponType(int code, String message) {
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
    }}
