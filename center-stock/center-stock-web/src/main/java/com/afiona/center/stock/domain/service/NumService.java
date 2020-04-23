package com.afiona.center.stock.domain.service;

import com.afiona.common.pojo.JsonResult;

/**
 * 库存数量操作
 *
 * @author dengweiyi
 * @date 2020-02-25
 */
public interface NumService <C>{
    /**
     * 冻结库存
     *
     * @param command
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    JsonResult<Void> freeze(C command);

    /**
     * 解冻库存
     *
     * @param command
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    JsonResult<Void> unfreeze(C command);

    /**
     * 扣减库存
     *
     * @param command
     * @param fromFrozen
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    JsonResult<Void> reduce(C command, boolean fromFrozen);

    /**
     * 增加库存
     *
     * @param command
     * @param allowedCreate
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    JsonResult<Void> increase(C command, boolean allowedCreate);
}