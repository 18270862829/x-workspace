package com.afiona.center.user.api;

import com.afiona.center.user.domain.model.User;
import com.afiona.common.pojo.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: lujunhan
 * @Date: 2020/04/02 11:11
 * @Description:
 **/

@RestController
@RequestMapping("/api/v1/user/backend/user")
@Api(value = "用户管理", tags = "用户管理")
public interface UserApi {

    /**
     * 新增单个用户
     *
     * @param user 用户信息
     * @return JsonResult 是否成功
     */
    @PostMapping
    JsonResult<Boolean> insertOne(@RequestBody User user);
}
