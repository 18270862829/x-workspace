package com.afiona.center.user.infrastructure.repo.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.afiona.center.user.constants.enums.UserResultEnum;
import com.afiona.center.user.constants.enums.UserType;
import com.afiona.center.user.domain.model.User;
import com.afiona.center.user.domain.model.WechatUser;
import com.afiona.center.user.domain.repo.UserRepository;
import com.afiona.center.user.domain.repo.WechatUserRepository;
import com.afiona.center.user.domain.service.UserService;
import com.afiona.center.user.infrastructure.dao.UserDAO;
import com.afiona.center.user.infrastructure.dao.WechatUserDAO;
import com.afiona.center.user.infrastructure.model.UserDO;
import com.afiona.center.user.infrastructure.model.WechatUserDO;
import com.afiona.common.pojo.JsonResult;
import com.afiona.common.util.CloneUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 微信账号repo实现
 *
 * @author dengweiyi
 * @date 2020-03-05
 */
@Repository
@Slf4j
public class WechatUserRepositoryImpl implements WechatUserRepository {
    @Resource
    private WechatUserDAO wechatUserDAO;

    @Resource
    private UserService userService;

    @Resource
    private UserRepository userRepository;

    @Resource
    private UserDAO userDAO;

    @Override
    public JsonResult<WechatUser> me() {
        User user = userService.me();
        if(user == null){
            return JsonResult.create(UserResultEnum.NOT_LOGIN);
        }
        if(user.getUserType() != UserType.WECHAT){
            return JsonResult.create(UserResultEnum.WRONG_TYPE);
        }
        log.info("<<debug user>>" + JSONUtil.toJsonStr(user));
        WechatUserDO wechatUserDO = wechatUserDAO.getOne(new LambdaQueryWrapper<WechatUserDO>().eq(WechatUserDO::getUserId, user.getId()));
        if(wechatUserDO == null){
            return JsonResult.create(UserResultEnum.EXCEPTION);
        }
        WechatUser wechatUser = CloneUtil.clone(wechatUserDO, WechatUser.class);
        wechatUser.setUser(user);
        return JsonResult.create(wechatUser);
    }

    @Override
    public List<WechatUser> listByIds(List<Long> wechatUserIds) {
        List<WechatUserDO> wechatUserDOS = wechatUserDAO.list(new LambdaQueryWrapper<WechatUserDO>().in(WechatUserDO::getId, wechatUserIds));
        List<WechatUser> wechatUsers = CloneUtil.cloneList(wechatUserDOS, WechatUser.class);

        List<Long> userIds = wechatUsers.stream().map(WechatUser::getUserId).collect(Collectors.toList());
        List<User> users = userRepository.listByIds(userIds);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(User::getId, Function.identity()));
        for(WechatUser wechatUser : wechatUsers){
            User user = userMap.get(wechatUser.getUserId());
            if(user == null){
                continue;
            }
            wechatUser.setUser(user);
        }
        return wechatUsers;
    }

    @Override
    public JsonResult<Long> store(WechatUser wechatUser) {
        if(wechatUser.getUser() == null){
            return JsonResult.create(UserResultEnum.NOT_EXIST.getCode(), "用户不存在");
        }
        UserDO userDO = CloneUtil.clone(wechatUser.getUser(), UserDO.class);
        userDAO.save(userDO);

        wechatUser.setUserId(userDO.getId());
        WechatUserDO wechatUserDO = CloneUtil.clone(wechatUser, WechatUserDO.class);
        wechatUserDAO.save(wechatUserDO);
        return JsonResult.create(wechatUserDO.getId());
    }

    @Override
    public JsonResult<Void> delete(long wechatUserId) {
        WechatUserDO wechatUserDO = wechatUserDAO.getById(wechatUserId);
        if(wechatUserDO == null){
            return JsonResult.create(UserResultEnum.NOT_EXIST.getCode(), "微信账号不存在");
        }
        UserDO userDO = userDAO.getById(wechatUserDO.getUserId());
        if(userDO == null){
            return JsonResult.create(UserResultEnum.NOT_EXIST.getCode(), "用户不存在");
        }
        wechatUserDAO.removeById(wechatUserId);
        userDAO.removeById(userDO.getId());
        return JsonResult.create();
    }

    @Override
    public WechatUser getByOpenId(String openId) {
        WechatUserDO wechatUserDO = wechatUserDAO.getOne(new LambdaQueryWrapper<WechatUserDO>().eq(WechatUserDO::getOpenId, openId));
        if(wechatUserDO == null){
            return null;
        }
        List<WechatUser> wechatUsers = listByIds(Lists.newArrayList(wechatUserDO.getId()));
        if(CollectionUtil.isEmpty(wechatUsers)){
            return null;
        }
        return wechatUsers.get(0);
    }
}
