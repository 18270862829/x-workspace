package com.afiona.center.stock.infrastructure.dao.impl;

import com.afiona.center.stock.infrastructure.dao.PhysicalStockDAO;
import com.afiona.center.stock.infrastructure.mapper.PhysicalStockMapper;
import com.afiona.center.stock.infrastructure.model.PhysicalStockDO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 实物库存DAO实现
 *
 * @author dengweiyi
 * @date 2020-02-25
 */
@Repository
public class PhysicalStockDAOImpl extends ServiceImpl<PhysicalStockMapper, PhysicalStockDO> implements PhysicalStockDAO {
}
