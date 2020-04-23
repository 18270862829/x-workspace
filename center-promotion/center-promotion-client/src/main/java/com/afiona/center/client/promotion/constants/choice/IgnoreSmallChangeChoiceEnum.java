package com.afiona.center.client.promotion.constants.choice;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * 抹零选择枚举
 *
 * @author dengweiyi
 * @date 2020-02-09
 */
public enum  IgnoreSmallChangeChoiceEnum implements IEnum<Integer> {
    /**
     * 抹去分+角
     */
    FEN_AND_JIAO(0, "抹去分+角"),
    /**
     * 抹去分
     */
    ONLY_FEN(1, "抹去分"),
    ;

    IgnoreSmallChangeChoiceEnum(int code, String message) {
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
