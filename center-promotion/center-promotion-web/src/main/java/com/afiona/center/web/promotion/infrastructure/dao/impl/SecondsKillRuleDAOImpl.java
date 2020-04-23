package com.afiona.center.web.promotion.infrastructure.dao.impl;

import com.afiona.center.web.promotion.infrastructure.dao.SecondsKillRuleDAO;
import com.afiona.center.web.promotion.infrastructure.mapper.SecondsKillRuleMapper;
import com.afiona.center.web.promotion.infrastructure.model.SecondsKillRuleDO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

/**
 * 秒杀规则dao实现
 *
 * @author LiJinXing
 * @date 2020/4/7
 */
@Component
public class SecondsKillRuleDAOImpl extends ServiceImpl<SecondsKillRuleMapper, SecondsKillRuleDO> implements SecondsKillRuleDAO {
}
