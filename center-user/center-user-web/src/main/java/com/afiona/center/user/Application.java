package com.afiona.center.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 启动类
 *
 * @author dengweiyi
 * @date 2020-03-05
 */
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.afiona.center.client.consultant", "com.afiona.center.member"})
@EnableDiscoveryClient
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class);
    }
}
