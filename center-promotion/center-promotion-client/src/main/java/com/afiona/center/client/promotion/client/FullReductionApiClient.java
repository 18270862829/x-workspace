package com.afiona.center.client.promotion.client;

import com.afiona.center.client.promotion.api.FullReductionApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 满减促销活动管理feign client
 *
 * @author dengweiyi
 * @date 2020-02-10
 */
@FeignClient("center-promotion")
public interface FullReductionApiClient extends FullReductionApi {
}
