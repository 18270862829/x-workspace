package com.afiona.center.stock.infrastructure.mapper;

import com.afiona.center.stock.infrastructure.model.PhysicalStockDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 实物库存mapper
 *
 * @author dengweiyi
 * @date 2020-02-25
 */
@Mapper
public interface PhysicalStockMapper extends BaseMapper<PhysicalStockDO> {
}
