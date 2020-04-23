package com.afiona.center.client.promotion.constants;

import com.afiona.common.enums.BaseEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * 错误码枚举
 *
 * @author dengweiyi
 * @date 2020-02-10
 */
public enum PromotionResultEnum implements BaseEnum {
    EMPTY(30101, "参数为空"),
    REPEAT(30102, "重复"),
    NOT_EXIST(30103, "不存在"),
    DATA_ERROR(30104, "数据异常"),
    NOT_TODAY(30105, "不在活动时间"),
    CHANNEL_UNQUALIFIED(30106, "不在适用渠道"),
    MEMBER_UNQUALIFIED(30107, "不满足适用对象"),
    GOODS_UNQUALIFIED(30108, "不满足适用商品"),
    EXCEED_LIMIT(30109, "超过参与次数"),
    LESS_THAN_THRESHOLD(30110, "不满足优惠门槛"),
    NOT_SUPPORT(30111, "不支持次促销活动类型"),

    ;
    @EnumValue
    private Integer code;
    private String msg;

    PromotionResultEnum(Integer code, String msg) {
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
    }}