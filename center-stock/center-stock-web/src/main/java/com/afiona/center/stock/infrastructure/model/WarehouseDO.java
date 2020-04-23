package com.afiona.center.stock.infrastructure.model;

import com.afiona.center.stock.constants.enums.WarehouseStatus;
import com.afiona.center.stock.constants.enums.WarehouseType;
import com.afiona.common.model.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 仓库DO
 *
 * @author dengweiyi
 * @date 2020-02-25
 */
@Data
@TableName(value = "warehouse")
public class WarehouseDO extends SuperEntity {
    /**
     * 仓库类型
     */
    private WarehouseType type;

    /**
     * 仓库名称
     */
    private String name;

    /**
     * 仓库编码
     */
    private String code;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String area;

    /**
     * 详细地址
     */
    private String detailedAddress;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 联系人
     */
    private String contactor;

    /**
     * 联系电话
     */
    private String contactNumber;

    /**
     * 仓库状态（0-禁用，1-启用）
     */
    private WarehouseStatus status;
}
