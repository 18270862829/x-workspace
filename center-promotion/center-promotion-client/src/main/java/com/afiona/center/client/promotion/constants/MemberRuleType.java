package com.afiona.center.client.promotion.constants;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author CLB
 * @Description 会员限制类型
 * @Date 2020/1/2 16:25
 */
public enum MemberRuleType implements IEnum<Integer> {

    /**
     * 全部用户
     */
    ALL(0, "全部商品"),

    /**
     * 指定会员等级
     */
    MEMBER_LEVEL(1, "指定会员等级"),

    /**
     * 指定会员
     */
    MEMBER_ID(2, "指定会员"),
    ;
    @EnumValue
    private int code;

    private String message;

    MemberRuleType(int code, String message) {
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
