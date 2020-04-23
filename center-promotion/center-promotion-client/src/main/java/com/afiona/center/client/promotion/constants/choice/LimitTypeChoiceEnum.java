package com.afiona.center.client.promotion.constants.choice;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * 限购设置
 *
 * @author LiJinXing
 * @date 2020/4/10
 */
public enum  LimitTypeChoiceEnum implements IEnum<Integer> {

    /**
     * 不限购
     */
    NOT_LIMIT(0, "不限购"),

    /**
     * 限购
     */
    LIMIT(1, "限购");

    LimitTypeChoiceEnum(int code, String message) {
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
    }
}
