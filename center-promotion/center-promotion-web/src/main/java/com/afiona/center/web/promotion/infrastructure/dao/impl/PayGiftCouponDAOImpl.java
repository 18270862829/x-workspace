package com.afiona.center.web.promotion.infrastructure.dao.impl;

import com.afiona.center.web.promotion.infrastructure.dao.PayGiftCouponDAO;
import com.afiona.center.web.promotion.infrastructure.mapper.PayGiftCouponMapper;
import com.afiona.center.web.promotion.infrastructure.model.PayGiftCouponDO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

/**
 * 支付有礼关联优惠券dao实现
 *
 * @author LiJinXing
 * @date 2020/2/25
 */
@Component
public class PayGiftCouponDAOImpl extends ServiceImpl<PayGiftCouponMapper, PayGiftCouponDO> implements PayGiftCouponDAO {
}
