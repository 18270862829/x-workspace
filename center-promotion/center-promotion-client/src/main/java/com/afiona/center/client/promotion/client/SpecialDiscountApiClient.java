package com.afiona.center.client.promotion.client;

import com.afiona.center.client.promotion.api.SpecialDiscountApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 限时折扣促销活动管理feign client
 *
 * @author LiJinXing
 * @date 2020/2/23
 */
@FeignClient("center-promotion")
public interface SpecialDiscountApiClient extends SpecialDiscountApi {
}
