package com.afiona.center.client.promotion.constants;

import com.afiona.common.enums.BaseEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * 返回错误信息
 *
 * @author LiJinXing
 * @date 2020/3/25
 */
public enum  ResultEnum implements BaseEnum {

    /**
     * 参数为空
     */
    EMPTY(40000, "参数为空"),

    /**
     * 未查询到数据
     */
    DATA_EMPTY(40001,"未查询到数据")
;

    @EnumValue
    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
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
