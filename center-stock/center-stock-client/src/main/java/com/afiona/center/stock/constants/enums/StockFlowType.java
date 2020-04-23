package com.afiona.center.stock.constants.enums;

import com.google.common.collect.Lists;

import com.baomidou.mybatisplus.core.enums.IEnum;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 库存流水类型
 *
 * @author dengweiyi
 * @date 2020-02-27
 */
public enum StockFlowType implements IEnum<Integer> {
    /**
     * 销售出库
     */
    UN_KNOWN(0, "未知", false),

    /**
     * 销售出库
     */
    SALE_OUT(1, "销售出库", false),

    /**
     * 调拨出库
     */
    TRANSFER_OUT(2, "调拨出库", false),

    /**
     * 调整出库
     */
    ADJUST_OUT(3, "调整出库", false),

    /**
     * 盘点出库
     */
    INVENTORY_OUT(4, "盘点出库", false),

    /**
     * 采购入库
     */
    PURCHASE_IN(5, "采购入库", true),

    /**
     * 退货入库
     */
    REFUND_IN(6, "退货入库", true),

    /**
     * 调拨入库
     */
    TRANSFER_IN(7, "调拨入库", true),

    /**
     * 调整入库
     */
    ADJUST_IN(8, "调整入库", true),

    /**
     * 盘点入库
     */
    INVENTORY_IN(9, "盘点入库", true),

    /**
     * 其他入库
     */
    OTHER_IN(10, "其他入库", true),

    ;

    StockFlowType(int code, String msg, boolean increase) {
        this.code = code;
        this.msg = msg;
        this.increase = increase;
    }

    private final int code;
    private final String msg;
    private final boolean increase;


    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isIncrease() {
        return increase;
    }

    @Override
    public Integer getValue() {
        return code;
    }

    /**
     * 查询是入库还是出库
     */
    public static List<StockFlowType> findByType(Boolean increase) {
        if (increase == null) {
            return Lists.newArrayList();
        }
        List<StockFlowType> list = Lists.newArrayList(StockFlowType.values());
        Boolean finalIncrease = increase;
        return list.stream().filter(e -> e.isIncrease() == finalIncrease).collect(Collectors.toList());
    }
}
