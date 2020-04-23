package com.afiona.center.web.promotion.infrastructure.dao.impl;

import com.afiona.center.web.promotion.infrastructure.dao.FullReductionRuleDAO;
import com.afiona.center.web.promotion.infrastructure.mapper.FullReductionRuleMapper;
import com.afiona.center.web.promotion.infrastructure.model.FullReductionRuleDO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

/**
 * 满减规则dao实现
 *
 * @author LiJinXing
 * @date 2020/2/13
 */
@Component
public class FullReductionRuleDAOImpl extends ServiceImpl<FullReductionRuleMapper, FullReductionRuleDO> implements FullReductionRuleDAO {
}
