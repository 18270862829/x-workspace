package com.afiona.center.stock.model.query;

import com.afiona.common.pojo.PageQuery;
import lombok.Data;

import java.util.List;

/**
 * 销售库存分页查询
 *
 * @author dengweiyi
 * @date 2020-02-25
 */
@Data
public class StockPageQuery extends PageQuery {
    /**
     * 渠道ID列表
     */
    private List<Long> storeIds;
}
