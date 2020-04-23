package com.afiona.center.client.promotion.client;

import com.afiona.center.client.promotion.api.ActivityApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 活动
 *
 * @author LiJinXing
 * @date 2020/3/4
 */
@FeignClient("center-promotion")
public interface ActivityApiClient extends ActivityApi {

}