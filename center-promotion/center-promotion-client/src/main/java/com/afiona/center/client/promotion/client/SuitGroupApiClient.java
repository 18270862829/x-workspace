package com.afiona.center.client.promotion.client;

import com.afiona.center.client.promotion.api.SuitGroupApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 组合套装促销活动管理 feign client
 *
 * @author LiJinXing
 * @date 2020/2/23
 */
@FeignClient("center-promotion")
public interface SuitGroupApiClient extends SuitGroupApi {
}
