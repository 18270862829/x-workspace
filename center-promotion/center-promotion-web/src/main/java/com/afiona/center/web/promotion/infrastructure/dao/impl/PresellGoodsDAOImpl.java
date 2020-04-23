package com.afiona.center.web.promotion.infrastructure.dao.impl;

import com.afiona.center.web.promotion.infrastructure.dao.PresellGoodsDAO;
import com.afiona.center.web.promotion.infrastructure.mapper.PresellGoodsMapper;
import com.afiona.center.web.promotion.infrastructure.model.PresellGoodsDO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

/**
 * 预售商品dao实现
 *
 * @author LiJinXing
 * @date 2020/4/22
 */
@Component
public class PresellGoodsDAOImpl extends ServiceImpl<PresellGoodsMapper, PresellGoodsDO> implements PresellGoodsDAO {
}
