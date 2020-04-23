package com.afiona.center.client.promotion.constants.choice;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 活动状态
 *
 * @author LiJinXing
 * @date 2020/3/4
 */
public enum ActivityStatus implements IEnum<Integer> {

    /**
     * 未开始
     */
    HAS_NOT_STARTED(0, "未开始"),

    /**
     * 已结束
     */
    OVER(2, "已结束"),

    /**
     * 进行中
     */
    PROCESSING(1, "进行中"),
    ;

    @EnumValue
    private int code;

    private String message;

    ActivityStatus(int code, String message) {
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
