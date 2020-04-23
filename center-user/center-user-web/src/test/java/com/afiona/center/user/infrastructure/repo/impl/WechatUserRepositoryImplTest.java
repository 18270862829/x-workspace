package com.afiona.center.user.infrastructure.repo.impl;

import com.afiona.center.user.Application;
import com.afiona.center.user.domain.model.User;
import com.afiona.center.user.domain.model.WechatUser;
import com.afiona.center.user.domain.repo.WechatUserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class WechatUserRepositoryImplTest {
    @Resource
    private WechatUserRepository wechatUserRepository;

    @Test
    public void store() {
        User user = new User();
        user.setId(1L);
        WechatUser wechatUser = new WechatUser();
        wechatUser.setUser(user);

        wechatUser.setNickname("\uD83C\uDF4A");
        wechatUser.setAvatorUrl("https://wx.qlogo.cn/mmopen/vi_32/xOibY6oHwicoyOkc2eibJYmDg6lEgKNOibff4XSdQJVq3ib7oY4ibEibEjicsgM3Jc0zhfToHFqyeMtRabgnr1uF2Tte7w/132");
        wechatUserRepository.store(wechatUser);
    }
}