package com.afiona.center.stock.model.query;

import com.afiona.common.pojo.PageQuery;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 渠道库存分布详情查询
 *
 * @author dengweiyi
 * @date 2020-04-11
 */
@Data
@ApiModel("店铺库存详情查询条件")
public class StoreDistributionDetailsQuery extends PageQuery {
    /**
     * 店铺ID
     */
    private long storeId;
}
