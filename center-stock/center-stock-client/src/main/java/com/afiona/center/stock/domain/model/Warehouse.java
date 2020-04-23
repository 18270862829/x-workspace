package com.afiona.center.stock.domain.model;

import com.afiona.center.stock.constants.enums.WarehouseStatus;
import com.afiona.center.stock.constants.enums.WarehouseType;
import com.afiona.common.model.SuperEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 仓库
 *
 * @author dengweiyi
 * @date 2020-01-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("仓库")
public class Warehouse extends SuperEntity {
    /**
     * 仓库类型
     */
    @ApiModelProperty("仓库类型")
    private WarehouseType type;

    /**
     * 仓库名称
     */
    @ApiModelProperty("仓库名称")
    private String name;

    /**
     * 仓库编码
     */
    @ApiModelProperty("仓库编码")
    private String code;

    /**
     * 地址
     */
    private Address address;

    /**
     * 联系人
     */
    @ApiModelProperty("联系人")
    private String contactor;

    /**
     * 联系电话
     */
    @ApiModelProperty("联系电话")
    private String contactNumber;

    /**
     * 仓库状态（0-禁用，1-启用）
     */
    @ApiModelProperty("仓库状态（0-禁用，1-启用）")
    private WarehouseStatus status;

    @Data
    @AllArgsConstructor
    @ApiModel("地址")
    public static class Address{
        /**
         * 所属区域
         */
        private Regin regin;

        /**
         * 详细地址
         */
        @ApiModelProperty("详细地址")
        private String detailedAddress;

        /**
         * 具体位置（用于地图定位）
         */
        private Position position;
    }

    @Data
    @AllArgsConstructor
    @ApiModel("所属区域")
    public static class Regin{
        /**
         * 省
         */
        @ApiModelProperty("省")
        private String province;

        /**
         * 市
         */
        @ApiModelProperty("市")
        private String city;

        /**
         * 区
         */
        @ApiModelProperty("区")
        private String area;
    }

    @Data
    @AllArgsConstructor
    @ApiModel("位置")
    public static class Position{
        /**
         * 经度
         */
        @ApiModelProperty("经度")
        private double longitude;

        /**
         * 纬度
         */
        @ApiModelProperty("纬度")
        private double latitude;
    }
}
