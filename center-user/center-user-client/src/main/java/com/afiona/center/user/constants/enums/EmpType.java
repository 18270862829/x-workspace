package com.afiona.center.user.constants.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @author: lujunhan
 * @date: 2020/3/25 11:17
 */

@Getter
public enum EmpType {

    SHOP_DIRECTOR(0, "店长"),
    SHOP_ASSISTANT(1, "助店"),
    RESERVE(2, "储备"),
    CONSULTANT(3, "美顾"),
    TRAINEES(4, "培训学员"),
    INSIDE_EMP(5, "内部员工");

    @EnumValue
    @JsonValue
    private int code;

    private String desc;

    EmpType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
