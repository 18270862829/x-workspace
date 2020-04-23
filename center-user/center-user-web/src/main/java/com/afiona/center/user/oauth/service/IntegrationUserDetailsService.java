package com.afiona.center.user.oauth.service;

import com.afiona.center.user.builder.WechatUserBuilder;
import com.afiona.center.user.domain.model.User;
import com.afiona.center.user.domain.model.WechatUser;
import com.afiona.center.user.domain.repo.UserRepository;
import com.afiona.center.user.domain.repo.WechatUserRepository;
import com.afiona.center.user.domain.service.MemberService;
import com.afiona.center.user.oauth.exception.InternalException;
import com.afiona.common.pojo.JsonResult;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.afiona.center.user.util.HeaderHelper.*;
import static com.afiona.center.user.constants.UserConstants.*;

/**
 * 集成用户服务
 *
 * @author dengweiyi
 * @date 2020-03-04
 */
@Service
@Slf4j
public class IntegrationUserDetailsService implements UserDetailsService {
    @Resource
    private UserRepository userRepository;

    @Resource
    private WechatUserBuilder wechatUserBuilder;

    @Resource
    private WechatUserRepository wechatUserRepository;

    @Resource
    private MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getByName(username);
        if(user != null){
            return user;
        }

        String scope = getParamValue("scope");
        String encryptedData = getParamValue("encryptedData");
        String iv = getParamValue("iv");
        String rawData = getParamValue("rawData");
        String signature = getParamValue("signature");
        if(!Strings.isNullOrEmpty(scope) && WECHAT_SCOPE.equals(scope)){
            // 微信授权登录时，username=code
            WechatUser wechatUser = wechatUserBuilder.build(username, encryptedData, iv, rawData, signature);
            WechatUser oldWechatUser = wechatUserRepository.getByOpenId(wechatUser.getOpenId());
            if(oldWechatUser != null){
                log.info("<<debug wechat user>> found old user=" + wechatUser.getOpenId());
                return oldWechatUser.getUser();
            }

            JsonResult<Long> result = wechatUserRepository.store(wechatUser);
            if(!result.success()){
                throw new InternalException("保存微信账号失败");
            }
            wechatUser.setId(result.getData());
            memberService.createMember(wechatUser);
            return wechatUser.getUser();
        }
        throw new UsernameNotFoundException("用户名不存在");
    }
}
