package com.afiona.center.web.promotion.infrastructure.dao.impl;

import com.afiona.center.web.promotion.infrastructure.dao.PresellRuleDAO;
import com.afiona.center.web.promotion.infrastructure.mapper.PresellRuleMapper;
import com.afiona.center.web.promotion.infrastructure.model.PresellRuleDO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

/**
 * 预售规则dao实现
 *
 * @author LiJinXing
 * @date 2020/4/22
 */
@Component
public class PresellRuleDAOImpl extends ServiceImpl<PresellRuleMapper, PresellRuleDO> implements PresellRuleDAO {
}
