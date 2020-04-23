package com.afiona.center.client.promotion.constants;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 搭配商品类型
 *
 * @author LiJinXing
 * @date 2020/2/19
 */
public enum SuitGoodsType implements IEnum<Integer> {

    /**
     * 主要商品
     */
    PRIMARY_GOODS(0,"主要商品"),

    /**
     * 搭配商品
     */
    MATCH_GOODS(1,"搭配商品");
    @EnumValue
    private int code;

    private String message;

    SuitGoodsType(int code, String message) {
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
