package com.afiona.center.web.promotion.infrastructure.dao.impl;

import com.afiona.center.web.promotion.infrastructure.dao.SuitRuleDAO;
import com.afiona.center.web.promotion.infrastructure.mapper.SuitRuleMapper;
import com.afiona.center.web.promotion.infrastructure.model.SuitRuleDO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

/**
 * 适用规则dao实现
 *
 * @author LiJinXing
 * @date 2020/2/13
 */
@Component
public class SuitRuleDAOImpl extends ServiceImpl<SuitRuleMapper, SuitRuleDO> implements SuitRuleDAO {
}
