package com.afiona.center.stock.util;

import lombok.Data;

/**
 * 范围
 *
 * @author dengweiyi
 * @date 2020-02-27
 */
@Data
public class Range <T> {
    /**
     * 最小值
     */
    private T min;

    /**
     * 最大值
     */
    private T max;
}
