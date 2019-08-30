package com.yss.gateway.filter;

import com.yss.gateway.config.PropertiesConfig;
import com.yss.gateway.entity.AuthToken;
import com.yss.gateway.entity.Users;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.List;
import java.util.Map;

public class TokenFilter implements GlobalFilter, Ordered {

    @Autowired
    private PropertiesConfig propertiesConfig;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //List<String> all = propertiesConfig.handleUri();
        String uri = exchange.getRequest().getPath().pathWithinApplication().value();
        System.out.println("dfw==访问的url为：" + uri);

        if(uri.endsWith("/deng/info")){
            String username = exchange.getRequest().getQueryParams().get("username").toString();
            String password = exchange.getRequest().getQueryParams().get("password").toString();
            String token = exchange.getRequest().getQueryParams().get("token").toString();

            Principal user = parseIdentity(token,username,password);
            if (user == null) {   //如果token认证失败user为null
                return exchange.getResponse().setComplete();
            }

            for (Map.Entry<String, String> entry : Users.USERS_MAP.entrySet()) {
                if (entry.getKey().equals(username) && entry.getValue().equals(password)) {
                    return chain.filter(exchange);
                }
            }
        }
        return chain.filter(exchange);

        /*for (String url : all) {
            //过滤不需要拥有token的连接
            if (uri.endsWith(url)) {
                System.out.println("不需要拥有token的uri：" + uri);
                return chain.filter(exchange);
            }
        }

        String token = exchange.getRequest().getQueryParams().getFirst("token");
        if (token == null || token.isEmpty()) {
            exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
            System.out.println("需要传token，否则请求无法通过");
            return exchange.getResponse().setComplete();
        }*/
        //return chain.filter(exchange);


    }

    @Override
    public int getOrder() {
        return -100;
    }



    private Principal parseIdentity(String token,String username,String password) {
        if(!StringUtils.isEmpty(token) && !StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)){
            AuthToken authToken = new AuthToken();
            authToken.setToken(token);
            authToken.setUsername(username);
            authToken.setPassword(password);
            return authToken;
        }else{
            return null;
        }

    }
}