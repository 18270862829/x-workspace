package com.afiona.center.user.constants.enums;

import com.afiona.common.enums.BaseEnum;

/**
 * 返回结果枚举
 *
 * @author dengweiyi
 * @date 2020-03-09
 */
public enum UserResultEnum implements BaseEnum {
    NOT_LOGIN(60001, "未登录"),
    WRONG_TYPE(60002, "账号类型错误"),
    NOT_EXIST(60003, "不存在"),

    EXCEPTION(60099, "程序异常"),

    ;

    UserResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;
    private String msg;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }}
