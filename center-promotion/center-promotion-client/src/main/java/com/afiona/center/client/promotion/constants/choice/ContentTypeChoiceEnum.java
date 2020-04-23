package com.afiona.center.client.promotion.constants.choice;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * 优惠内容类型选择枚举
 *
 * @author dengweiyi
 * @date 2020-02-09
 */
public enum  ContentTypeChoiceEnum implements IEnum<Integer> {
    /**
     * 订单金额优惠
     */
    AMOUNT(0, "订单金额优惠",40000000L),
    /**
     * 送礼
     */
    GIFT(1, "送礼",20000000L),
    /**
     * 包邮
     */
    FREE_SHIPPING(2, "包邮",10000000L),
    ;

    ContentTypeChoiceEnum(int code, String message,Long priority) {
        this.code = code;
        this.message = message;
        this.priority=priority;
    }

    @EnumValue
    private final int code;
    private final String message;
    private final Long priority;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Long getPriority(){return priority;}

    @Override
    public Integer getValue() {
        return code;
    }}
