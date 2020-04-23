package com.afiona.center.client.promotion.constants;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * 领取状态
 *
 * @author LiJinXing
 * @date 2020/4/14
 */
public enum  GiveOutStatusEnum implements IEnum<Integer> {

    /**
     * 未领取
     */
    NO_GIVEOUT(0, "未领取"),

    /**
     * 自动领取
     */
    AUTO_GIVEOUT(1, "自动领取"),

    /**
     * 手动领取
     */
    HAND_GIVEOUT(2, "手动领取"),
    ;
    @EnumValue
    private int code;

    private String message;

    GiveOutStatusEnum(int code, String message) {
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
