package com.yss.gateway.config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Arrays;
import java.util.List;

/**
 * @author WuShukai
 * @version V1.0
 * @description 读取自定义的properties
 * @date 2018/12/14  11:17
 */

@Configuration
//配置前缀
@ConfigurationProperties(prefix = "gateway.filter")
//配置文件名
@PropertySource("classpath:/gateway-filter-uri.properties")
public class PropertiesConfig {

    //前缀名（gateway.filter） + 后缀名（uri）
    private String uri;

    //将定义的uri转化成list
    public List<String> handleUri() {
        return Arrays.asList(uri.split(","));
    }


    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }


}