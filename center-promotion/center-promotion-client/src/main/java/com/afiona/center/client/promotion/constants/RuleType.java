package com.afiona.center.client.promotion.constants;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author CLB
 * @Description 限制规则类型
 * @Date 2020/1/2 16:25
 */
public enum RuleType implements IEnum<Integer> {

    /**
     * 商品规则
     */
    GOODS_RULE(0, "商品规则"),

    /**
     * 用户规则
     */
    MEMBER_RULE(1, "用户规则"),
    ;
    @EnumValue
    private int code;

    private String message;

    RuleType(int code, String message) {
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
