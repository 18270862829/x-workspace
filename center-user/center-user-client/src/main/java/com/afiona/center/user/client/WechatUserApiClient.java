package com.afiona.center.user.client;

import com.afiona.center.user.api.WechatUserApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 微信账号管理feignClient
 *
 * @author dengweiyi
 * @date 2020-03-09
 */
@FeignClient("center-user")
public interface WechatUserApiClient extends WechatUserApi {
}
