package com.yql.biz.support.validation;

import com.yql.biz.exception.JatRequestExpiredException;
import com.yql.biz.model.Jat;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author wangxiaohong
 */
@Component
public class JatTimestampValidator implements Validator<Jat> {
    @Override
    public boolean isValid(Jat source) {
        Timestamp timestamp = source.getTimestamp();
        if (timestamp == null) {
            throw new JatRequestExpiredException();
        }
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        if (LocalDateTime.now().minusSeconds(10).isAfter(localDateTime)) {//请求超过10秒就过期
            throw new JatRequestExpiredException();
        }
        return true;
    }
}
