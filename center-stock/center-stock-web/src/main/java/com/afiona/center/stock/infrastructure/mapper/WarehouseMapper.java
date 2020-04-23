package com.afiona.center.stock.infrastructure.mapper;

import com.afiona.center.stock.infrastructure.model.WarehouseDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 仓库mapper
 *
 * @author dengweiyi
 * @date 2020-02-25
 */
@Mapper
public interface WarehouseMapper extends BaseMapper<WarehouseDO> {
}
