package com.yql.biz.support;

import com.yql.biz.model.Jat;
import org.springframework.stereotype.Component;

/**
 * @author wangxiaohong
 */
@Component
public class EndStringUserCodeResolver implements UserCodeResolver {
    private static final int USER_CODE_LENGTH = 8;

    @Override
    public String resolve(Jat jat) {
        String token = jat.getToken();
        return token.substring(token.length() - USER_CODE_LENGTH);
    }
}
