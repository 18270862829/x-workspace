package com.afiona.center.user.domain.repo;

import com.afiona.center.user.domain.model.WechatUser;
import com.afiona.common.pojo.JsonResult;

import java.util.List;

/**
 * 微信账号repo实现
 *
 * @author dengweiyi
 * @date 2020-03-05
 */
public interface WechatUserRepository {
    /**
     * 查询当前登录微信账号
     *
     * @param
     * @return : com.afiona.common.pojo.JsonResult<com.afiona.center.user.domain.model.WechatUser>
     */
    JsonResult<WechatUser> me();

    /**
     * 列表查询
     *
     * @param wechatUserIds
     * @return : com.afiona.common.pojo.JsonResult<com.afiona.center.user.domain.model.WechatUser>
     */
    List<WechatUser> listByIds(List<Long> wechatUserIds);

    /**
     * 保存
     *
     * @param wechatUser
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    JsonResult<Long> store(WechatUser wechatUser);

    /**
     * 删除
     *
     * @param wechatUserId
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    JsonResult<Void> delete(long wechatUserId);

    /**
     * 查询（根据openId）
     *
     * @param openId
     * @return : com.afiona.center.user.domain.model.WechatUser
     */
    WechatUser getByOpenId(String openId);
}
