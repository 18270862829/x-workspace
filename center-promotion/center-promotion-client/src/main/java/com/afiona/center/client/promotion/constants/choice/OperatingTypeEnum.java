package com.afiona.center.client.promotion.constants.choice;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * 操作类型
 *
 * @author LiJinXing
 * @date 2020/3/25
 */
public enum  OperatingTypeEnum  implements IEnum<Integer> {

    /**
     * 启用
     */
    OPERATING_START(0, "启用"),

    /**
     * 停用
     */
    OPERATING_STOP(1, "停用"),

    /**
     * 编辑
     */
    OPERATING_UPDATE(2, "编辑"),

    /**
     * 查看
     */
    OPERATING_QUERY(3, "查看"),

    /**
     * 删除
     */
    OPERATING_DELETE(4, "删除"),

    /**
     * 复制
     */
    OPERATING_COPY(5, "复制");

    OperatingTypeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
    @EnumValue
    private final int code;
    private final String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


    @Override
    public Integer getValue() {
        return code;
    }
}
