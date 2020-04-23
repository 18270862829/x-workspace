package com.afiona.center.client.promotion.constants.choice;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * 送礼选择枚举
 *
 * @author dengweiyi
 * @date 2020-02-09
 */
public enum  GiftChoiceEnum implements IEnum<Integer> {
    /**
     * 未选择
     */
    NO_SELECTED(0, "未选择",0L),
    /**
     * 送积分
     */
    SCORE(1, "送积分",1000000L),
    /**
     * 送优惠劵
     */
    COUPON(2, "送优惠劵",2000000L),
    /**
     * 送赠品
     */
    GOODS(3, "送赠品",4000000L),
    ;

    GiftChoiceEnum(int code, String message,Long priority) {
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
