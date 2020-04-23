package com.afiona.center.stock.client;

import com.afiona.center.stock.api.PhysicalStockApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 实物库存管理feignClient
 *
 * @author dengweiyi
 * @date 2020-02-25
 */
@FeignClient("center-stock")
public interface PhysicalStockApiClient extends PhysicalStockApi {
}
