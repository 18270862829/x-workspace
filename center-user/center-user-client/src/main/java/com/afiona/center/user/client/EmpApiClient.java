package com.afiona.center.user.client;

import com.afiona.center.user.api.EmpApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 员工管理feignClient
 *
 * @author dengweiyi
 * @date 2020-03-09
 */
@FeignClient("center-user")
public interface EmpApiClient extends EmpApi {
}
