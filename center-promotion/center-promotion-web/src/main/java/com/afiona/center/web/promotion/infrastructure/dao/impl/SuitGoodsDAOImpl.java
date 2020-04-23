package com.afiona.center.web.promotion.infrastructure.dao.impl;

import com.afiona.center.web.promotion.infrastructure.dao.SuitGoodsDAO;
import com.afiona.center.web.promotion.infrastructure.mapper.SuitGoodsMapper;
import com.afiona.center.web.promotion.infrastructure.model.SuitGoodsDO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

/**
 * 适用商品dao实现
 *
 * @author LiJinXing
 * @date 2020/2/13
 */
@Component
public class SuitGoodsDAOImpl extends ServiceImpl<SuitGoodsMapper, SuitGoodsDO> implements SuitGoodsDAO {
}
