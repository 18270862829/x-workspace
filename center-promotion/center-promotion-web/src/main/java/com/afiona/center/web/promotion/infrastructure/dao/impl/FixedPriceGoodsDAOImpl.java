package com.afiona.center.web.promotion.infrastructure.dao.impl;

import com.afiona.center.web.promotion.infrastructure.dao.FixedPriceGoodsDAO;
import com.afiona.center.web.promotion.infrastructure.mapper.FixedPriceGoodsMapper;
import com.afiona.center.web.promotion.infrastructure.model.FixedPriceGoodsDO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

/**
 * 一口价规则DAO实现
 *
 * @author LiJinXing
 * @date 2020/2/13
 */
@Component
public class FixedPriceGoodsDAOImpl extends ServiceImpl<FixedPriceGoodsMapper, FixedPriceGoodsDO> implements FixedPriceGoodsDAO {
}
