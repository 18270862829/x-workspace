package com.afiona.center.user.infrastructure.dao.impl;

import com.afiona.center.user.infrastructure.dao.WechatUserDAO;
import com.afiona.center.user.infrastructure.mapper.WechatUserMapper;
import com.afiona.center.user.infrastructure.model.WechatUserDO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 微信账号DAO实现
 *
 * @author dengweiyi
 * @date 2020-03-05
 */
@Repository
public class WechatUserDAOImpl extends ServiceImpl<WechatUserMapper, WechatUserDO> implements WechatUserDAO {
}
