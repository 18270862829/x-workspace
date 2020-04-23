package com.afiona.center.stock.domain.service;
/**
 * 销售库存更新操作
 *
 * @author dengweiyi
 * @date 2020-02-26
 */
public interface StockUpdateService {
    /**
     * 根据实物库存计算销售库存
     *
     * @param
     * @return : void
     */
    void update();
}
