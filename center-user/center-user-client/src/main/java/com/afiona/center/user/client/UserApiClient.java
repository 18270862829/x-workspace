package com.afiona.center.user.client;

import com.afiona.center.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: lujunhan
 * @Date: 2020/04/02 11:14
 * @Description:
 **/

@FeignClient("center-user")
public interface UserApiClient extends UserApi {
}
