package com.afiona.center.user.domain.repo.verify.service;

import cn.hutool.core.util.ObjectUtil;
import com.afiona.center.client.consultant.constants.common.GlobalResultEnum;
import com.afiona.center.user.domain.model.User;
import com.afiona.center.user.infrastructure.dao.UserDAO;
import com.afiona.center.user.infrastructure.model.UserDO;
import com.afiona.common.util.BizException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author: lujunhan
 * @date: 2020/4/2 11:26
 */

@Component
public class UserVerifyService {

    @Resource
    private UserDAO userDAO;

    public void insertOneVerify(User user) {
        UserDO userDO = userDAO.getOne(new LambdaQueryWrapper<UserDO>()
                .eq(UserDO::getUsername, user.getUsername()));
        if (ObjectUtil.isNotNull(userDO)) {
            throw new BizException("用户名已存在", GlobalResultEnum.SUCCESS.getCode());
        }
    }
}
