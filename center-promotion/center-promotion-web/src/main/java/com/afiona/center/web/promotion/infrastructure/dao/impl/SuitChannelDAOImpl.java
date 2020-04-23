package com.afiona.center.web.promotion.infrastructure.dao.impl;

import com.afiona.center.web.promotion.infrastructure.dao.SuitChannelDAO;
import com.afiona.center.web.promotion.infrastructure.mapper.SuitChannelMapper;
import com.afiona.center.web.promotion.infrastructure.model.SuitChannelDO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

/**
 * 适用渠道dao实现
 *
 * @author LiJinXing
 * @date 2020/2/13
 */
@Component
public class SuitChannelDAOImpl extends ServiceImpl<SuitChannelMapper, SuitChannelDO> implements SuitChannelDAO {
}
