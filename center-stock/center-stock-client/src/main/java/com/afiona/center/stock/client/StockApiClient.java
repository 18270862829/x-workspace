package com.afiona.center.stock.client;

import com.afiona.center.stock.api.StockApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 销售库存管理feignClient
 *
 * @author dengweiyi
 * @date 2020-02-25
 */
@FeignClient("center-stock")
public interface StockApiClient extends StockApi {
}
