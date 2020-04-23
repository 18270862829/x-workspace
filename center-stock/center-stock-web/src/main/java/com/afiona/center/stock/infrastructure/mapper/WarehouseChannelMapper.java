package com.afiona.center.stock.infrastructure.mapper;

import com.afiona.center.stock.infrastructure.model.WarehouseStoreDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 仓库渠道关联关系mapper
 *
 * @author dengweiyi
 * @date 2020-02-25
 */
@Mapper
public interface WarehouseChannelMapper extends BaseMapper<WarehouseStoreDO> {
}
