package com.afiona.center.user.oauth.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 微信调用异常
 *
 * @author dengweiyi
 * @date 2020-03-05
 */
public class WechatInvokeException extends AuthenticationException {
    public WechatInvokeException(String msg, Throwable t) {
        super(msg, t);
    }

    public WechatInvokeException(String msg) {
        super(msg);
    }
}
