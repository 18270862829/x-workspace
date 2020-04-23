package com.afiona.center.user.api.impl;

import com.afiona.center.user.api.UserApi;
import com.afiona.center.user.domain.model.User;
import com.afiona.center.user.domain.repo.UserRepository;
import com.afiona.common.pojo.JsonResult;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: lujunhan
 * @date: 2020/4/2 11:15
 */

@RestController
public class UserApiImpl implements UserApi {

    @Resource
    private UserRepository userRepository;


    @ApiOperation("新增单个用户")
    @Override
    public JsonResult<Boolean> insertOne(@RequestBody @ApiParam(required = true, value = "用户信息") @Validated(User.Create.class) User user) {
        return JsonResult.create(userRepository.insertOne(user));
    }
}
