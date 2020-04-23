package com.afiona.center.client.promotion.client;

import com.afiona.center.client.promotion.api.FixedPriceApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 一口价促销活动管理 feign client
 *
 * @author LiJinXing
 * @date 2020/2/23
 */
@FeignClient("center-promotion")
public interface FixedPriceApiClient extends FixedPriceApi {
}
