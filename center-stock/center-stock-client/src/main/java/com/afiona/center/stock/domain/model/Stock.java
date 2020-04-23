package com.afiona.center.stock.domain.model;

import com.afiona.common.model.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 销售库存
 *
 * @author dengweiyi
 * @date 2020-01-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("销售库存")
public class Stock extends SuperEntity {
    /**
     * SKU ID
     */
    @ApiModelProperty("sku id")
    private Long skuId;

    /**
     * 店铺ID
     */
    @ApiModelProperty("店铺ID")
    private Long storeId;

    /**
     * 可用库存
     */
    @ApiModelProperty("可用库存")
    private Integer available;

    /**
     * 冻结库存
     */
    @ApiModelProperty("冻结库存")
    private Integer frozen;

    public int updateVersion(){
        int oldVersion = getVersion();
        setVersion(oldVersion + 1);
        return oldVersion;
    }
}
