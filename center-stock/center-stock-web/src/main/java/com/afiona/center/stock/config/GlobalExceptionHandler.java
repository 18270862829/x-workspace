package com.afiona.center.stock.config;

import com.afiona.common.pojo.JsonResult;
import com.afiona.common.util.BatchOperateFailException;
import com.afiona.common.util.BizException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ZhangShuaiQiang
 * @date 2019/10/31 15:07
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String DEFAULT_MESSAGE = "系统内部错误internal error";

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JsonResult onException(HttpServletRequest request, Exception ex) {
        LOGGER.error(DEFAULT_MESSAGE, ex);
        LOGGER.error("stack:\n{}", ex.getStackTrace());
        recordRequestMsg(request);
        if (ex instanceof BizException) {
            BizException bizException = (BizException) ex;
            return JsonResult.create(bizException.getCode(), bizException.getMessage());
        } else if (ex instanceof BatchOperateFailException) {
            return JsonResult.create(500, ex.getMessage());
        }
        return JsonResult.create(500, DEFAULT_MESSAGE, ex.getStackTrace());
    }

    private void recordRequestMsg(HttpServletRequest request) {
        LOGGER.error("URL:        {}", request.getRequestURL());
        LOGGER.error("HTTP Method:{}", request.getMethod());
        LOGGER.error("IP:         {}", request.getRemoteAddr());
    }
}
