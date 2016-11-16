package com.yql.biz.exception;

import com.yql.biz.exception.MessageRuntimeException;

/**
 * @author wangxiaohong
 */
public class JatRequestExpiredException extends MessageRuntimeException {
    public JatRequestExpiredException() {
        super("com.yql.biz.gateway.exception.RequestExpired.message");
    }
}
