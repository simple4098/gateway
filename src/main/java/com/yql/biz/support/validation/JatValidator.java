package com.yql.biz.support.validation;

import com.yql.biz.model.Jat;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangxiaohong
 */
@Component
public class JatValidator implements Validator<Jat> {

    @Resource
    private List<Validator<Jat>> validators;

    @Override
    public boolean isValid(Jat source) {
        validators.forEach(validator -> validator.isValid(source));
        return true;
    }
}
