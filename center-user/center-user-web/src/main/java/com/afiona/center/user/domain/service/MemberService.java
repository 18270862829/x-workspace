package com.afiona.center.user.domain.service;

import com.afiona.center.member.client.MemberClient;
import com.afiona.center.member.domain.model.Member;
import com.afiona.center.user.domain.model.WechatUser;
import com.afiona.common.pojo.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 会员服务
 *
 * @author dengweiyi
 * @date 2020-03-10
 */
@Service
@Slf4j
public class MemberService {
    @Resource
    private MemberClient memberClient;

    public void createMember(WechatUser wechatUser){
        Member member = new Member();
        member.setWechatUserId(wechatUser.getId());
        member.setOpenid(wechatUser.getOpenId());
        member.setIconPath(wechatUser.getAvatorUrl());
        member.setNickname(wechatUser.getNickname());
        JsonResult<Long> result = memberClient.insert(member);
        if(!result.success()){
            log.error("创建会员失败");
        }
    }
}
