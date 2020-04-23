package com.afiona.center.client.promotion.constants;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author CLB
 * @Description 商品限制类型
 * @Date 2020/1/2 16:25
 */
public enum GoodsRuleType implements IEnum<Integer> {

    /**
     * 全部商品
     */
    ALL(0, "全部商品"),

    /**
     * 指定品牌
     */
    APPOINTED_BRAND(1, "指定品牌"),

    /**
     * 指定品类
     */
    APPOINTED_CATEGORY(2, "指定品类"),

    /**
     * 指定商品
     */
    APPOINTED_SPU(3, "指定商品"),
    ;
    @EnumValue
    private int code;

    private String message;

    GoodsRuleType(int code, String message) {
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
