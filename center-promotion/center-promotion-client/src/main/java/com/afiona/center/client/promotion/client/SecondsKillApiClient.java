package com.afiona.center.client.promotion.client;

import com.afiona.center.client.promotion.api.SecondsKillApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 秒杀apiClient
 *
 * @author LiJinXing
 * @date 2020/4/10
 */
@FeignClient("center-promotion")
public interface SecondsKillApiClient extends SecondsKillApi {
}
