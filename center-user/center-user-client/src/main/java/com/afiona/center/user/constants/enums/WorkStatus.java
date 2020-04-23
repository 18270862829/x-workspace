package com.afiona.center.user.constants.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @author: lujunhan
 * @date: 2020/3/25 11:17
 */

@Getter
public enum WorkStatus {

    LEAVE_POSITION(0, "离职"),
    ON_POSITION(1, "在职");

    @EnumValue
    @JsonValue
    private int code;

    private String desc;

    WorkStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
