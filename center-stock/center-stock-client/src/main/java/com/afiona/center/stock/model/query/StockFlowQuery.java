package com.afiona.center.stock.model.query;

import com.afiona.center.stock.constants.enums.StockFlowType;
import com.afiona.center.stock.util.Range;
import com.afiona.common.pojo.PageQuery;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 库存流水查询
 *
 * @author dengweiyi
 * @date 2020-02-27
 */
@Data
public class StockFlowQuery extends PageQuery {
    /**
     * SKU ID
     */
    @ApiModelProperty("SKU ID")
    private Long skuId;

    /**
     * 单据类型
     */
    @ApiModelProperty("单据类型")
    private StockFlowType flowType;

    /**
     * 单据编号
     */
    @ApiModelProperty("关联单号")
    private String code;

    /**
     * 关联单号
     */
    @ApiModelProperty("关联单号")
    private String relatedCode;

    /**
     * 制单人
     */
    @ApiModelProperty("制单人")
    private String createdBy;

    /**
     * 仓库名称
     */
    @ApiModelProperty("仓库名称")
    private String warehouseName;

    /**
     * 仓库Id
     */
    @ApiModelProperty("仓库Id")
    private Long warehouseId;

    /**
     * 制单时间
     */
    @ApiModelProperty("制单时间")
    private Range<Date> range;

    /**
     * 是否是入库
     */
    @ApiModelProperty("是否是入库 true则是入库 false则是出库")
    private Boolean increase;
}
