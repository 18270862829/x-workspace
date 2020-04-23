package com.afiona.center.client.promotion.constants.choice;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * 发劵方式选择枚举
 *
 * @author dengweiyi
 * @date 2020-02-09
 */
public enum  CoupondistributeChoiceEnum implements IEnum<Integer> {
    AUTO(0, "自动发放至账户"),
    BY_HAND(1, "手动领取"),
    NOT_PUBLISH(2, "暂不发布"),
    ;

    CoupondistributeChoiceEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
    @EnumValue
    private final int code;
    private final String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


    @Override
    public Integer getValue() {
        return code;
    }}
