package com.afiona.center.web.promotion.infrastructure.dao.impl;

import com.afiona.center.web.promotion.infrastructure.dao.CouponTemplateDAO;
import com.afiona.center.web.promotion.infrastructure.mapper.CouponTemplateMapper;
import com.afiona.center.web.promotion.infrastructure.model.CouponTemplateDO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

/**
 *优惠券模板DAO实现
 *
 * @author LiJinXing
 * @date 2020/2/13
 */
@Component
public class CouponTemplateDAOImpl extends ServiceImpl<CouponTemplateMapper, CouponTemplateDO> implements CouponTemplateDAO {
}
