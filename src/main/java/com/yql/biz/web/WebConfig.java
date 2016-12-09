package com.yql.biz.web;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangxiaohong
 */
@Configuration
public class WebConfig {

    @Bean
    @ConditionalOnProperty(prefix = "access.filter", name = "enabled", havingValue = "true")
    @ConfigurationProperties(prefix = "access.filter")
    public AccessFilter accessFilter() {
        return new AccessFilter();
    }

}
