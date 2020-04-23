package com.afiona.common.pojo;

import com.afiona.common.enums.BaseEnum;
import com.afiona.common.serializer.ResponseSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

/**
 * @author wangyu
 * @date 2019/11/06 17:20
 * <p>
 * 示例：
 * <p>
 * JsonResult<Void> result0 = JsonResult.create();
 * JsonResult<Void> result1 = JsonResult.create(100, "123");
 * JsonResult<Object> result3 = JsonResult.create(new Object());
 * JsonResult<Object> result4 = JsonResult.create(100, "123",new Object());
 * if(result0.isSuccess()){
 * // do something
 * }
 */
//@JsonSerialize(using = ResponseSerializer.class)
public class JsonResult<T> implements Serializable {
    private static final long serialVersionUID = -4920653262189505144L;

    private static final int SUCCESS_CODE = 200;
    private static final String SUCCESS_MSG = "success";

    /**
     * 响应业务状态
     */
    private final int code;

    /**
     * 响应消息
     */
    private final String msg;

    /**
     * 响应数据
     */
    private T data;

    public static <T> JsonResult<T> create(T data) {
        return new JsonResult<>(SUCCESS_CODE, SUCCESS_MSG, data);
    }

    public static <T> JsonResult<T> create(int code, String msg) {
        return new JsonResult<>(code, msg, null);
    }

    public static <T> JsonResult<T> create(int code, String msg, T data) {
        return new JsonResult<>(code, msg, data);
    }

    public static <T> JsonResult<T> create(BaseEnum baseEnum) {
        return create(baseEnum.getCode(), baseEnum.getMsg());
    }

    public static <T> JsonResult<T> create() {
        return new JsonResult<>();
    }

    private JsonResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private JsonResult() {
        this(SUCCESS_CODE, SUCCESS_MSG, null);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public boolean success() {
        return SUCCESS_CODE == code;
    }

    @Override
    public String toString() {
        return "JsonResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}

