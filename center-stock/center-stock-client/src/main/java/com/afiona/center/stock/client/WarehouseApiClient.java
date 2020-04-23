package com.afiona.center.stock.client;

import com.afiona.center.stock.api.WarehouseApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 仓库管理feignClient
 *
 * @author dengweiyi
 * @date 2020-02-25
 */
@FeignClient("center-stock")
public interface WarehouseApiClient extends WarehouseApi {
}
