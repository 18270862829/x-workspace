package com.afiona.center.client.promotion.constants.choice;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * 金额优惠选择枚举
 *
 * @author dengweiyi
 * @date 2020-02-09
 */
public enum  AmountChoiceEnum implements IEnum<Integer> {
    /**
     * 未选择
     */
    NO_SELECTED(0, "未选择",0L),
    /**
     * 减x元
     */
    AMOUNT(1, "减x元",3000000L),
    /**
     * 打折
     */
    DISCOUNT(2, "打折",2000000L),
    ;

    AmountChoiceEnum(int code, String message,Long priority) {
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
