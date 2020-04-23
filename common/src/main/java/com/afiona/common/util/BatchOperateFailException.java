package com.afiona.common.util;
/**
 * 批量操作失败异常（用于回滚）
 *
 * @author dengweiyi
 * @date 2020-01-06
 */
public class BatchOperateFailException extends RuntimeException{
    public BatchOperateFailException(String message) {
        super(message);
    }
}
