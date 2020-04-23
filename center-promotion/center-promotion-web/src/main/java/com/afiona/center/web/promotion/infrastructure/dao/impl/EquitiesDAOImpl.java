package com.afiona.center.web.promotion.infrastructure.dao.impl;

import com.afiona.center.web.promotion.infrastructure.dao.EquitiesDAO;
import com.afiona.center.web.promotion.infrastructure.mapper.EquitiesMapper;
import com.afiona.center.web.promotion.infrastructure.model.EquitiesDO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

/**
 * 权益dao实现
 *
 * @author LiJinXing
 * @date 2020/4/10
 */
@Component
public class EquitiesDAOImpl extends ServiceImpl<EquitiesMapper, EquitiesDO> implements EquitiesDAO {
}
