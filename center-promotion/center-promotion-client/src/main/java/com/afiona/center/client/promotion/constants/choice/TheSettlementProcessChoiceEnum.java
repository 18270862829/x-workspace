package com.afiona.center.client.promotion.constants.choice;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * 结算流程类型
 *
 * @author LiJinXing
 * @date 2020/4/7
 */
public enum  TheSettlementProcessChoiceEnum implements IEnum<Integer> {

    /**
     * 与普通商品混合结算，允许加入购物车
     */
    ADD_SHOPPING_TROLLEY(0, "与普通商品混合结算，允许加入购物车"),
    /**
     * 单独结算，不允许加入购物车
     */
    NOT_ADD_SHOPPING_TROLLEY(1, "单独结算，不允许加入购物车");

    TheSettlementProcessChoiceEnum(int code, String message) {
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
