package com.afiona.center.stock.client;

import com.afiona.center.stock.api.StockAdminApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 库存管理feignClient
 *
 * @author dengweiyi
 * @date 2020-02-25
 */
@FeignClient("center-stock")
public interface StockAdminApiClient extends StockAdminApi {
}
