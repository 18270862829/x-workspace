package com.afiona.center.client.promotion.client;

import com.afiona.center.client.promotion.api.CouponTemplateApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 优惠劵促销活动管理feignclient
 *
 * @author dengweiyi
 * @date 2020-02-10
 */
@FeignClient("center-promotion")
public interface CouponTemplateApiClient extends CouponTemplateApi {
}
