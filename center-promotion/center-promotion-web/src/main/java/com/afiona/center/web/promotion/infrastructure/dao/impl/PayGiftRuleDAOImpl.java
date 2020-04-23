package com.afiona.center.web.promotion.infrastructure.dao.impl;

import com.afiona.center.web.promotion.infrastructure.dao.PayGiftRuleDAO;
import com.afiona.center.web.promotion.infrastructure.mapper.PayGiftRuleMapper;
import com.afiona.center.web.promotion.infrastructure.model.PayGiftRuleDO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

/**
 * 支付有礼规则dao实现
 *
 * @author LiJinXing
 * @date 2020/2/25
 */
@Component
public class PayGiftRuleDAOImpl extends ServiceImpl<PayGiftRuleMapper, PayGiftRuleDO> implements PayGiftRuleDAO {
}
