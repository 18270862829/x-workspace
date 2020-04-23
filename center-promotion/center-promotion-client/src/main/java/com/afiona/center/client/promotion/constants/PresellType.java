package com.afiona.center.client.promotion.constants;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * 预售类型
 *
 * @author LiJinXing
 * @date 2020/4/22
 */
public enum  PresellType implements IEnum<Integer> {

    /**
     * 全款预售
     */
    FULL_PAYMENT_PRESELL(0, "全款预售"),
    /**
     * 定金膨胀
     */
    EARNEST_MONET_INFLATE(1, "定金膨胀"),
            ;

    PresellType(int code, String message) {
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
