package com.afiona.center.user.domain.service;

import cn.hutool.core.collection.CollectionUtil;
import com.afiona.center.user.Application;
import com.afiona.center.user.domain.model.WechatUser;
import com.afiona.center.user.domain.repo.WechatUserRepository;
import com.afiona.common.pojo.JsonResult;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class MemberServiceTest {
    @Resource
    private MemberService memberService;

    @Resource
    private WechatUserRepository wechatUserRepository;

    @Test
    public void createMember() {
        List<WechatUser> wechatUsers = wechatUserRepository.listByIds(Lists.newArrayList(7L));
        if(CollectionUtil.isEmpty(wechatUsers)){
            return;
        }
        WechatUser wechatUser = wechatUsers.get(0);
        memberService.createMember(wechatUser);
    }
}