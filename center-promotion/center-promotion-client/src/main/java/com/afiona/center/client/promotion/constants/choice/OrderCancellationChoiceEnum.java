package com.afiona.center.client.promotion.constants.choice;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * 订单取消类型
 *
 * @author LiJinXing
 * @date 2020/4/7
 */
public enum  OrderCancellationChoiceEnum implements IEnum<Integer> {

    /**
     * 与普通订单一致
     */
    GENERAL_ORDER_TYPE(0, "与普通订单一致"),
    /**
     * 买家X分钟未支付订单，订单自动取消
     */
    AUTO_CANCEL(1, "单独结算，不允许加入购物车");

    OrderCancellationChoiceEnum(int code, String message) {
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
