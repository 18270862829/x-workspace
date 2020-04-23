package com.afiona.center.client.promotion.client;

import com.afiona.center.client.promotion.api.AddMoneyToBuyApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 加价购促销活动管理feign client
 *
 * @author LiJinXing
 * @date 2020/2/23
 */
@FeignClient("center-promotion")
public interface AddMoneyToBuyApiClient extends AddMoneyToBuyApi {
}
