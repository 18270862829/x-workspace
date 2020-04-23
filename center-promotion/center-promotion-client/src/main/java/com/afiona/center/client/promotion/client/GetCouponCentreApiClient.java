package com.afiona.center.client.promotion.client;

import com.afiona.center.client.promotion.api.GetCouponCentreApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 领券中心 管理 feign client
 *
 * @author LiJinXing
 * @date 2020/3/5
 */
@FeignClient("center-promotion")
public interface GetCouponCentreApiClient extends GetCouponCentreApi {
}
