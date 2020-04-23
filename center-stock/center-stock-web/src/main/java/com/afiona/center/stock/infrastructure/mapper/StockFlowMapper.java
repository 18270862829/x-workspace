package com.afiona.center.stock.infrastructure.mapper;

import com.afiona.center.stock.infrastructure.model.StockFlowDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 库存流水mapper
 *
 * @author dengweiyi
 * @date 2020-02-27
 */
@Mapper
public interface StockFlowMapper extends BaseMapper<StockFlowDO> {
}
