package com.yss.gateway.config;

import com.yss.gateway.filter.TokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class MyConfig {
    @Bean
    public TokenFilter tokenFilter() {
        return new TokenFilter();
    }

}