package com.afiona.center.web.promotion.infrastructure.dao.impl;

import com.afiona.center.web.promotion.infrastructure.dao.SpecialDiscountRuleDAO;
import com.afiona.center.web.promotion.infrastructure.mapper.SpecialDiscountRuleMapper;
import com.afiona.center.web.promotion.infrastructure.model.SpecialDiscountRuleDO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

/**
 * 限时折扣规则dao的实现
 *
 * @author LiJinXing
 * @date 2020/2/18
 */
@Component
public class SpecialDiscountRuleDAOImpl extends ServiceImpl<SpecialDiscountRuleMapper, SpecialDiscountRuleDO> implements SpecialDiscountRuleDAO {
}
