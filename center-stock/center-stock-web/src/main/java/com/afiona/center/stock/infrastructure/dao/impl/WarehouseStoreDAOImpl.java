package com.afiona.center.stock.infrastructure.dao.impl;

import com.afiona.center.stock.infrastructure.dao.WarehouseStoreDAO;
import com.afiona.center.stock.infrastructure.mapper.WarehouseChannelMapper;
import com.afiona.center.stock.infrastructure.model.WarehouseStoreDO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 仓库渠道关联关系DAO实现
 *
 * @author dengweiyi
 * @date 2020-02-25
 */
@Repository
public class WarehouseStoreDAOImpl extends ServiceImpl<WarehouseChannelMapper, WarehouseStoreDO> implements WarehouseStoreDAO {
}
