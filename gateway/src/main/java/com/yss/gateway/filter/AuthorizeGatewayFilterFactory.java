package com.yss.gateway.filter;

import com.yss.gateway.entity.AuthToken;
import com.yss.gateway.entity.Users;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class AuthorizeGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthorizeGatewayFilterFactory.Config> {

    private static final Log logger = LogFactory.getLog(AuthorizeGatewayFilterFactory.class);

    public AuthorizeGatewayFilterFactory() {
        super(Config.class);
        logger.info("Loaded GatewayFilterFactory [Authorize]");
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("enabled");
    }

    @Override
    public GatewayFilter apply(AuthorizeGatewayFilterFactory.Config config) {
        return (exchange, chain) -> {
            if (!config.isEnabled()) {
                return chain.filter(exchange);
            }

            String uri = exchange.getRequest().getPath().pathWithinApplication().value();

            ServerHttpResponse response = exchange.getResponse();

            String username = exchange.getRequest().getQueryParams().get("username").get(0);
            String password = exchange.getRequest().getQueryParams().get("password").get(0);
            String token = exchange.getRequest().getQueryParams().get("token").toString();

            Principal user = parseIdentity(token, username, password);
            if (user == null) {   //如果token认证失败user为null
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }

            boolean flag = false;

            for (Map.Entry<String, String> entry : Users.USERS_MAP.entrySet()) {
                if (entry.getKey().equals(username) && entry.getValue().equals(password)) {
                    flag = true;
                }
            }

            if (!flag) {
                return response.setComplete();
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {
        // 控制是否开启认证
        private boolean enabled;

        public Config() {
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }


    private Principal parseIdentity(String token, String username, String password) {
        if (!org.apache.commons.lang.StringUtils.isEmpty(token) && !org.apache.commons.lang.StringUtils.isEmpty(username) && !org.apache.commons.lang.StringUtils.isEmpty(password)) {
            AuthToken authToken = new AuthToken();
            authToken.setToken(token);
            authToken.setUsername(username);
            authToken.setPassword(password);
            return authToken;
        } else {
            return null;
        }

    }
}