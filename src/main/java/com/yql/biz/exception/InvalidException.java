package com.yql.biz.exception;

import com.yql.biz.exception.MessageRuntimeException;

/**
 * @author wangxiaohong
 */
abstract class InvalidException extends MessageRuntimeException {
    InvalidException(String messageKey) {
        super(messageKey);
    }
}
