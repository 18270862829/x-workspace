package com.afiona.center.client.promotion.constants;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author CLB
 * @Description 活动促销状态
 * @Date 2020/1/2 16:25
 */
public enum StatusType implements IEnum<Integer> {

    /**
     * 未开始
     */
    HAS_NOT_STARTED(0, "未开始"),

    /**
     * 进行中
     */
    PROCESSING(1, "进行中"),

    /**
     * 已结束
     */
    EXPIRED(2, "已结束"),

    /**
     * 审核中
     */
    AUDIT(3,"审核中");

    @EnumValue
    private int code;

    private String message;

    StatusType(int code, String message) {
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
