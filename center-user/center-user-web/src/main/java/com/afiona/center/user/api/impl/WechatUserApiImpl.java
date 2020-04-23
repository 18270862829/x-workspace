package com.afiona.center.user.api.impl;

import com.afiona.center.user.api.WechatUserApi;
import com.afiona.center.user.domain.model.WechatUser;
import com.afiona.center.user.domain.repo.WechatUserRepository;
import com.afiona.common.pojo.JsonResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 微信账号管理实现
 *
 * @author dengweiyi
 * @date 2020-03-09
 */
@RestController
public class WechatUserApiImpl implements WechatUserApi {
    @Resource
    private WechatUserRepository wechatUserRepository;

    @Override
    public JsonResult<WechatUser> me() {
        return wechatUserRepository.me();
    }

    @Override
    public JsonResult<List<WechatUser>> listByIds(@RequestBody List<Long> wechatUserIds) {
        return JsonResult.create(wechatUserRepository.listByIds(wechatUserIds));
    }

    @Override
    public JsonResult<Void> delete(long wechatUserId) {
        return wechatUserRepository.delete(wechatUserId);
    }
}
