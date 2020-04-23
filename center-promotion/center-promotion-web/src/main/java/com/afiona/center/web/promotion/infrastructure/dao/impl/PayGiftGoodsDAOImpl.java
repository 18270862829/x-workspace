package com.afiona.center.web.promotion.infrastructure.dao.impl;

import com.afiona.center.web.promotion.infrastructure.dao.PayGiftGoodsDAO;
import com.afiona.center.web.promotion.infrastructure.mapper.PayGiftGoodsMapper;
import com.afiona.center.web.promotion.infrastructure.model.PayGiftGoodsDO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

/**
 * 支付有礼关联赠品dao实现
 *
 * @author LiJinXing
 * @date 2020/2/25
 */
@Component
public class PayGiftGoodsDAOImpl extends ServiceImpl<PayGiftGoodsMapper, PayGiftGoodsDO> implements PayGiftGoodsDAO {
}
