package com.afiona.center.client.promotion.client;

import com.afiona.center.client.promotion.api.PresellApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 预售client
 *
 * @author LiJinXing
 * @date 2020/4/22
 */
@FeignClient("center-promotion")
public interface PresellApiClient extends PresellApi {
}
