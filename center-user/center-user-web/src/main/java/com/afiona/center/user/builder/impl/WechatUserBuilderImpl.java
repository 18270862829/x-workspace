package com.afiona.center.user.builder.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.WxMaUserService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.hutool.json.JSONUtil;
import com.afiona.center.user.builder.WechatUserBuilder;
import com.afiona.center.user.constants.enums.UserType;
import com.afiona.center.user.domain.model.User;
import com.afiona.center.user.domain.model.WechatUser;
import com.afiona.center.user.oauth.exception.WechatInvokeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 微信账号构建器
 *
 * @author dengweiyi
 * @date 2020-03-09
 */
@Component
@Slf4j
public class WechatUserBuilderImpl implements WechatUserBuilder {

    @Resource
    private WxMaService wxMaService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public WechatUser build(String code, String encryptedData, String iv, String rawData, String signature){
        try {
            WxMaUserService wxMaUserService = wxMaService.getUserService();
            WxMaJscode2SessionResult wxMaJscode2SessionResult = wxMaUserService.getSessionInfo(code);
            String sessionKey = wxMaJscode2SessionResult.getSessionKey();
            boolean flag = wxMaUserService.checkUserInfo(sessionKey, rawData, signature);
            if(!flag){
                log.error("用户信息校验失败, " + msg(code, encryptedData, iv));
                throw new WechatInvokeException("用户信息校验失败");
            }

            WxMaUserInfo wxMaUserInfo = wxMaUserService.getUserInfo(sessionKey, encryptedData, iv);
            log.info("wecaht user: " + JSONUtil.toJsonStr(wxMaUserInfo));

            WechatUser wechatUser = new WechatUser();
            wechatUser.setOpenId(wxMaUserInfo.getOpenId());
            wechatUser.setNickname(wxMaUserInfo.getNickName());
            wechatUser.setAvatorUrl(wxMaUserInfo.getAvatarUrl());
            wechatUser.setSessionKey(sessionKey);
            wechatUser.setUnionId(wxMaJscode2SessionResult.getUnionid());

            User user = new User();
            user.setUsername(code);
            user.setPassword(passwordEncoder.encode(""));
            user.setUserType(UserType.WECHAT);
            wechatUser.setUser(user);
            return wechatUser;
        }catch (Exception e){
            log.error("微信调用异常", e);
            throw new WechatInvokeException("微信调用异常 code:" + code + ",msg:" + e.getMessage(), e);
        }
    }

    private String msg(String code, String encryptedData, String iv){
        return String.format("code=%s, encryptedData=%s, iv=%s", code, encryptedData, iv);
    }
}
