package com.afiona.center.stock.infrastructure.mapper;


import com.afiona.center.stock.infrastructure.model.StockDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 库存mapper
 *
 * @author dengweiyi
 * @date 2019-09-29
 */

@Mapper
public interface StockMapper extends BaseMapper<StockDO> {
}
