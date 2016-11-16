package com.yql.biz.support.validation;

/**
 * @author wangxiaohong
 */
public interface Validator<S> {
    /**
     * @param source object what need to be validate
     * @return true if source is valid, otherwise InvalidException will be thrown.
     */
    boolean isValid(S source);
}
