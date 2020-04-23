package com.afiona.common.util;

import com.afiona.common.enums.BaseEnum;
import com.afiona.common.pojo.JsonResult;

import java.io.Serializable;

/**
 * @author wangyu
 * @date 2019/11/08 14:33
 */
public class BizException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = -1354043731946864103L;
    private int code;

    public BizException() {
    }

    public BizException(String msg) {
        super(msg);
        this.code = 500;
    }

    public BizException(String msg, int code) {
        super(msg);
        this.code = code;
    }

    public BizException(JsonResult jsonResult) {
        super(jsonResult.getMsg());
        this.code = jsonResult.getCode();
    }

    public BizException(BaseEnum baseEnum) {
        super(baseEnum.getMsg());
        this.code = baseEnum.getCode();
    }

    public int getCode() {
        return code;
    }
}
