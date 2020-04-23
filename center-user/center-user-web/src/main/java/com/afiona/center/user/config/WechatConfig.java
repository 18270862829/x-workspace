package com.afiona.center.user.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 微信配置
 *
 * @author dengweiyi
 * @date 2020-03-09
 */
@Component
public class WechatConfig {
    @Bean
    public WxMaService wxMaService(){
        WxMaServiceImpl wxMaService = new WxMaServiceImpl();
        wxMaService.setWxMaConfig(wxMaConfig());
        return wxMaService;
    }

    @Bean
    public WxMaConfig wxMaConfig(){
        WxMaDefaultConfigImpl wxMaDefaultConfig = new WxMaDefaultConfigImpl();
        wxMaDefaultConfig.setAppid("wx8e181f73e33b1d6c");
        wxMaDefaultConfig.setSecret("5bd9604939ac482da24562bad4b9dd69");
        return wxMaDefaultConfig;
    }
}
