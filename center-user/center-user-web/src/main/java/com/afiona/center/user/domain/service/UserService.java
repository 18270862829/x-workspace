package com.afiona.center.user.domain.service;

import com.afiona.center.user.domain.model.User;
import com.afiona.center.user.domain.repo.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户服务
 *
 * @author dengweiyi
 * @date 2020-03-09
 */
@Service
public class UserService {
    @Resource
    private UserRepository userRepository;

    public User me(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null){
            return null;
        }
        User user = (User) authentication.getPrincipal();
        User realUser = userRepository.getByName(user.getUsername());
        if(realUser == null){
            return null;
        }
        return realUser;
    }
}
