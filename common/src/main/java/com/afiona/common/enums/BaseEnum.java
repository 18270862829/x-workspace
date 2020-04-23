package com.afiona.common.enums;

/**
 * 基础枚举
 *
 * @author wangyu
 */
public interface BaseEnum {
    /**
     * 枚举key
     *
     * @return int
     */
    Integer getCode();

    /**
     * 描述信息
     *
     * @return desc
     */
    String getMsg();
}
