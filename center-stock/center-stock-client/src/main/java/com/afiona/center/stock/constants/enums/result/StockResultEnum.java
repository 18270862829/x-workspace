package com.afiona.center.stock.constants.enums.result;

import com.afiona.common.enums.BaseEnum;

/**
 * 返回结果
 *
 * @author dengweiyi
 * @date 2020-01-06
 */
public enum  StockResultEnum implements BaseEnum {
    EMPTY(50001, "参数为空"),
    NOT_EXIST(50002, "不存在"),
    NOT_ENOUGH(50003, "库存数量不足"),
    PARAM_ERROR(50004, "参数错误"),
    NO_NAME(50005, "名称缺失"),
    WRONG_REGIN(50006, "所属区域错误"),
    NO_DETAIL_ADDRESS(50007, "详细地址缺失"),
    WRONG_POSITION(50008, "经纬度错误"),
    CHANNEL_REPEAT(50009, "渠道重复"),
    INVENTORY_EXISTS(50010, "存在库存，不能禁用"),

    EXCEPTION(50100, "程序异常")
    ;
    private Integer code;
    private String msg;

    StockResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
