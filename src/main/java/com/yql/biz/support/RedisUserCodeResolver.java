package com.yql.biz.support;

import com.alibaba.fastjson.JSON;
import com.yql.biz.model.Jat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

/**
 * @author wangxiaohong
 */

public class RedisUserCodeResolver implements UserCodeResolver {

    @Value("${prefix.user.token:user:token:}")
    private String userTokenPrefix;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String resolve(Jat jat) {
        String token = jat.getToken();
        BoundValueOperations<String, String> ops = stringRedisTemplate.boundValueOps(userTokenPrefix + token);
        String s = ops.get();
        UserMode user = JSON.parseObject(s, UserMode.class);
        return user.getUserCode();
    }

    /*{
            "id": 5,
                "memberType": "GENERAL",
                "phone": "13800138000",
                "userCode": "jOFrRhYa",
                "userToken": "7IL67J8YlFBjfciya5iGShkakuuhuK3kjOFrRhYa",
                "userType": "VIP"
        }
     */
    private static class UserMode {
        private String userCode;
        private String userToken;
        private String userType;

        public String getUserCode() {
            return userCode;
        }

        public void setUserCode(String userCode) {
            this.userCode = userCode;
        }

        public String getUserToken() {
            return userToken;
        }

        public void setUserToken(String userToken) {
            this.userToken = userToken;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }
    }
}
