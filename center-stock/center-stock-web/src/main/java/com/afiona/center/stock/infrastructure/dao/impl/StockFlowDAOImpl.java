package com.afiona.center.stock.infrastructure.dao.impl;

import com.afiona.center.stock.infrastructure.dao.StockFlowDAO;
import com.afiona.center.stock.infrastructure.mapper.StockFlowMapper;
import com.afiona.center.stock.infrastructure.model.StockFlowDO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * 库存流水DAO实现
 *
 * @author dengweiyi
 * @date 2020-02-27
 */
@Repository
public class StockFlowDAOImpl extends ServiceImpl<StockFlowMapper, StockFlowDO> implements StockFlowDAO {
}
