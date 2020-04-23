package com.afiona.center.user.api;

import com.afiona.center.user.domain.model.WechatUser;
import com.afiona.common.pojo.JsonResult;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 微信账号
 *
 * @author dengweiyi
 * @date 2020-03-09
 */

@RestController
@RequestMapping("/user/wechat")
@Api(value = "微信用户管理", tags = "微信用户管理")
public interface WechatUserApi {
    /**
     * 查询当前登录微信账号
     *
     * @param
     * @return : com.afiona.common.pojo.JsonResult<com.afiona.center.user.domain.model.WechatUser>
     */
    @PostMapping("/me")
    JsonResult<WechatUser> me();

    /**
     * 列表查询
     *
     * @param wechatUserIds
     * @return : com.afiona.common.pojo.JsonResult<com.afiona.center.user.domain.model.WechatUser>
     */
    @PostMapping("/listByIds")
    JsonResult<List<WechatUser>> listByIds(@RequestBody List<Long> wechatUserIds);

    /**
     * 删除
     *
     * @param wechatUserId
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    @PostMapping("/delete")
    JsonResult<Void> delete(long wechatUserId);
}
