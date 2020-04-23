package com.afiona.center.stock.infrastructure.dao.impl;

import com.afiona.center.stock.infrastructure.dao.WarehouseDAO;
import com.afiona.center.stock.infrastructure.mapper.WarehouseMapper;
import com.afiona.center.stock.infrastructure.model.WarehouseDO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 仓库DAO实现
 *
 * @author dengweiyi
 * @date 2020-02-25
 */
@Repository
public class WarehouseDAOImpl extends ServiceImpl<WarehouseMapper, WarehouseDO> implements WarehouseDAO {
}
