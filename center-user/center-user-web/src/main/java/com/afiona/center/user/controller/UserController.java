package com.afiona.center.user.controller;

import com.afiona.center.user.domain.model.User;
import com.afiona.center.user.domain.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * TODO
 *
 * @author dengweiyi
 * @date 2020-03-09
 */
@RestController
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/me")
    public User me(){
        return userService.me();
    }
}
