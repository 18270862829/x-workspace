package com.afiona.center.user.oauth.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 内部错误异常
 *
 * @author dengweiyi
 * @date 2020-03-09
 */
public class InternalException extends AuthenticationException {
    public InternalException(String msg) {
        super(msg);
    }
}
