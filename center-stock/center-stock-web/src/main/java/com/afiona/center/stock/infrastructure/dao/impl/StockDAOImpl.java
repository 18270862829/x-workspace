package com.afiona.center.stock.infrastructure.dao.impl;

import com.afiona.center.stock.infrastructure.dao.StockDAO;
import com.afiona.center.stock.infrastructure.mapper.StockMapper;
import com.afiona.center.stock.infrastructure.model.StockDO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * @author ZhangShuaiQiang
 * @date 2019/12/30 下午2:43
 */

@Repository
public class StockDAOImpl extends ServiceImpl<StockMapper, StockDO>
        implements StockDAO {
}
