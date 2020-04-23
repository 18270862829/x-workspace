package com.afiona.center.stock.client;

import com.afiona.center.stock.api.StockFlowApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 库存流水feignClient
 *
 * @author dengweiyi
 * @date 2020-02-27
 */
@FeignClient("center-stock")
public interface StockFlowApiClient extends StockFlowApi {
}
