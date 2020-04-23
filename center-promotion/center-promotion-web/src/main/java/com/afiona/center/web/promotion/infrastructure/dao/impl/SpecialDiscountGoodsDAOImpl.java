package com.afiona.center.web.promotion.infrastructure.dao.impl;

import com.afiona.center.web.promotion.infrastructure.dao.SpecialDiscountGoodsDAO;
import com.afiona.center.web.promotion.infrastructure.mapper.SpecialDiscountGoodsMapper;
import com.afiona.center.web.promotion.infrastructure.model.SpecialDiscountGoodsDO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

/**
 * 限时特价促销活动关联商品DAO的实现
 *
 * @author LiJinXing
 * @date 2020/2/18
 */
@Component
public class SpecialDiscountGoodsDAOImpl extends ServiceImpl<SpecialDiscountGoodsMapper, SpecialDiscountGoodsDO> implements SpecialDiscountGoodsDAO {
}
