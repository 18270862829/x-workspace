package com.afiona.center.user.infrastructure.repo.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.afiona.center.user.domain.model.User;
import com.afiona.center.user.domain.repo.UserRepository;
import com.afiona.center.user.domain.repo.verify.service.UserVerifyService;
import com.afiona.center.user.infrastructure.dao.UserDAO;
import com.afiona.center.user.infrastructure.model.UserDO;
import com.afiona.common.util.CloneUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户repo实现
 *
 * @author dengweiyi
 * @date 2020-03-09
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    @Resource
    private UserDAO userDAO;

    @Resource
    private UserVerifyService userVerifyService;

    @Override
    public User getByName(String name) {
        UserDO userDO = userDAO.getOne(new LambdaQueryWrapper<UserDO>().eq(UserDO::getUsername, name));
        return CloneUtil.clone(userDO, User.class);
    }

    @Override
    public List<User> listByIds(List<Long> userIds) {
        if (CollectionUtil.isEmpty(userIds)) {
            return Lists.newArrayList();
        }
        List<UserDO> userDOS = userDAO.list(new LambdaQueryWrapper<UserDO>().in(UserDO::getId, userIds));
        List<User> users = CloneUtil.cloneList(userDOS, User.class);
        return users;
    }

    @Override
    public Boolean insertOne(User user) {
        userVerifyService.insertOneVerify(user);
        return userDAO.save(CloneUtil.clone(user, UserDO.class));
    }
}
