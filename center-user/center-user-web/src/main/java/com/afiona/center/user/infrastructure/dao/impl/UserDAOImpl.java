package com.afiona.center.user.infrastructure.dao.impl;

import com.afiona.center.user.infrastructure.dao.UserDAO;
import com.afiona.center.user.infrastructure.mapper.UserMapper;
import com.afiona.center.user.infrastructure.model.UserDO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 用户DAO实现
 *
 * @author dengweiyi
 * @date 2020-03-09
 */
@Repository
public class UserDAOImpl extends ServiceImpl<UserMapper, UserDO> implements UserDAO {
}
