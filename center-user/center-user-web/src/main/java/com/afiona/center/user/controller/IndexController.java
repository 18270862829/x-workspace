package com.afiona.center.user.controller;

import org.springframework.web.bind.annotation.RestController;

/**
 * controller
 *
 * @author dengweiyi
 * @date 2020-03-05
 */
@RestController
public class IndexController {
    public String index(){
        return "hello world";
    }
}
