package com.yql.biz.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author wangxiaohong
 */
public class Jat implements Serializable {
    private String alg;
    private String sign;
    private String appKey;
    private String token;
    private Timestamp timestamp;


    @Override
    public String toString() {
        return "｛appKey:'" + appKey + '\'' +
                ",token:'" + token + '\'' +
                ",timestamp:" + timestamp +
                '}';
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getAlg() {
        return alg;
    }

    public void setAlg(String alg) {
        this.alg = alg;
    }
}
