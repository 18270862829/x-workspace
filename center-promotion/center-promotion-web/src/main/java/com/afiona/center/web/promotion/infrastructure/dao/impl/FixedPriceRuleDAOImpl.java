package com.afiona.center.web.promotion.infrastructure.dao.impl;

import com.afiona.center.web.promotion.infrastructure.dao.FixedPriceRuleDAO;
import com.afiona.center.web.promotion.infrastructure.mapper.FixedPriceRuleMapper;
import com.afiona.center.web.promotion.infrastructure.model.FixedPriceRuleDO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

/**
 * 一口价规则DAO实现
 *
 * @author LiJinXing
 * @date 2020/2/13
 */
@Component
public class FixedPriceRuleDAOImpl extends ServiceImpl<FixedPriceRuleMapper, FixedPriceRuleDO> implements FixedPriceRuleDAO {
}
