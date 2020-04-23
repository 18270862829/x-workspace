package com.afiona.center.client.promotion.client;

import com.afiona.center.client.promotion.api.PayGiftApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 支付有礼促销活动管理 feign client
 *
 * @author LiJinXing
 * @date 2020/2/25
 */
@FeignClient("center-promotion")
public interface PayGiftApiClient extends PayGiftApi {
}
