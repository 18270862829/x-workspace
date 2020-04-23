package com.afiona.center.client.promotion.constants.choice;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 活动商品门槛规则类型
 *
 * @author LiJinXing
 * @date 2020/2/25
 */
public enum  GoodsThresholdRuleTypeEnum  implements IEnum<Integer> {

    /**
     * 所有的商品
     */
     ALL_GOODS(0,"所有商品"),

    /**
     * 部分的商品
     */
     SECTIONAL_GOODS(1,"不同商品"),

    /**
     * 相同的商品
     */
     SAME_GOODS(2,"相同的商品");
    @EnumValue
    private int code;

    private String message;

    GoodsThresholdRuleTypeEnum(int code, String message) {
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
