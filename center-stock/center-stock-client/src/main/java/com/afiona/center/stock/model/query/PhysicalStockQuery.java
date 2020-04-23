package com.afiona.center.stock.model.query;

import com.afiona.center.stock.util.Range;
import com.afiona.common.pojo.PageQuery;
import lombok.Data;

import java.util.List;

/**
 * 实物库存查询
 *
 * @author dengweiyi
 * @date 2020-02-25
 */
@Data
public class PhysicalStockQuery extends PageQuery {
    /**
     * SKU ID列表
     */
    private List<Long> skuIds;

    /**
     * 名称（仓库/门店）
     */
    private String name;

    /**
     * 实物库存范围
     */
    private Range<Integer> stockRange;
}
