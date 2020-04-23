package com.afiona.center.user.constants.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @author: lujunhan
 * @date: 2020/3/25 11:17
 */

@Getter
public enum CompilationType {

    INSIDE(0, "内部"),
    OUTSIDE(1, "外部");

    @EnumValue
    @JsonValue
    private int code;

    private String desc;

    CompilationType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
