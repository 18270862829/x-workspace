package com.afiona.center.user.constants.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * 用户类型枚举
 *
 * @author dengweiyi
 * @date 2020-03-09
 */
public enum UserType implements IEnum<Integer> {
    /**
     * 普通账号
     */
    NORMAL(0),

    /**
     * 微信账号
     */
    WECHAT(1),
    ;

    UserType(Integer value) {
        this.value = value;
    }

    private Integer value;


    @Override
    public Integer getValue() {
        return value;
    }
}
