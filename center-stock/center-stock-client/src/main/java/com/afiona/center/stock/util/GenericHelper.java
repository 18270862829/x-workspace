package com.afiona.center.stock.util;

import com.afiona.common.model.SuperEntity;
import com.afiona.common.util.BatchOperateFailException;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 泛型辅助类
 *
 * @author dengweiyi
 * @date 2020-02-10
 */
public class GenericHelper {
    public static <I extends SuperEntity> void safeUpdate(I item, IService<I> dao){
        // 更新版本号
        int oldVersion = item.getVersion();
        item.setVersion(oldVersion + 1);
        boolean success = dao.update(item, new UpdateWrapper<I>().lambda()
                .eq(I::getId, item.getId())
                .eq(I::getVersion, oldVersion));
        if(!success){
            throw new BatchOperateFailException("版本号变更");
        }
    }
}
