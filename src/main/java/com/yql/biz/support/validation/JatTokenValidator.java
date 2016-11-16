package com.yql.biz.support.validation;

import com.yql.biz.exception.JatTokenInvalidException;
import com.yql.biz.model.Jat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @author wangxiaohong
 */
@Component
public class JatTokenValidator implements Validator<Jat> {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Value("${prefix.user.token:user:token:}")
    private String userTokenPrefix;

    @Override
    public boolean isValid(Jat jat) {
        String token = jat.getToken();
        BoundValueOperations<String, String> ops = stringRedisTemplate.boundValueOps(userTokenPrefix + token);
        String s = ops.get();
        if (!StringUtils.hasText(s)) {
            throw new JatTokenInvalidException();
        }
        return true;
    }
}
