package com.example.demoserver.config;

import com.example.demoserver.entity.AuthToken;
import com.example.demoserver.entity.Authentication;
import com.example.demoserver.entity.Users;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketAutoConfig implements WebSocketMessageBrokerConfigurer {
//WebSocketMessageBrokerConfigurer AbstractWebSocketMessageBrokerConfigurer

    private static final Logger logger= LoggerFactory.getLogger(WebSocketAutoConfig.class);

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/deng")     //开启/deng端点
                /*.addInterceptors(new HandshakeInterceptor() {
                    *//**
                     * websocket握手
                     *//*
                    @Override
                    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
                        ServletServerHttpRequest req = (ServletServerHttpRequest) request;
                        //获取token认证
                        String token = req.getServletRequest().getParameter("token");
                        String username = req.getServletRequest().getParameter("username");
                        String password = req.getServletRequest().getParameter("password");
                        logger.info("dfw==握手之前");
                        //解析token获取用户信息
                        Principal user = parseIdentity(token,username,password);
                        if (user == null) {   //如果token认证失败user为null，返回false拒绝握手
                            return false;
                        }

                        for (Map.Entry<String, String> entry : Users.USERS_MAP.entrySet()) {
                            if (entry.getKey().equals(username) && entry.getValue().equals(password)) {
                                logger.info("dfw==用户名和密码正确");
                                //保存认证用户
                                attributes.put("user", user);
                                return true;
                            }
                        }
                        return false;
                    }

                    @Override
                    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
                        logger.info("dfw==握手之后");
                    }
                }).setHandshakeHandler(new DefaultHandshakeHandler() {
            @Override
            protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
                //设置认证用户
                logger.info("dfw==设置用户");
                return (Principal) attributes.get("user");
            }
        })*/
                .setAllowedOrigins("*")             //允许跨域访问
                .withSockJS();                      //使用sockJS
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        //订阅Broker名称
        //registry.enableSimpleBroker("/topic","/user");
        registry.enableSimpleBroker("/toAll");
        //全局使用的订阅前缀（客户端订阅路径上会体现出来）
        //registry.setApplicationDestinationPrefixes("/app/");
        //
        //点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认也是/user/
        //registry.setUserDestinationPrefix("/user/");
    }


    /*@Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.setInterceptors(new ChannelInterceptorAdapter() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                logger.info("dfw==1111111");
                //1. 判断是否首次连接请求
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    logger.info("dfw==首次连接");
                    //2. 验证是否登录
                    String username = accessor.getNativeHeader("username").get(0);
                    String password = accessor.getNativeHeader("password").get(0);
                    for (Map.Entry<String, String> entry : Users.USERS_MAP.entrySet()) {
                        if (entry.getKey().equals(username) && entry.getValue().equals(password)) {
                            logger.info("dfw==用户名和密码正确");
                            //验证成功,登录
                            Authentication user = new Authentication(username); // access authentication header(s)}
                            accessor.setUser(user);
                            return message;
                        }
                    }
                    return null;
                }else{
                    //不是首次连接，已经成功登陆
                    logger.info("dfw==不是首次连接");
                    return message;
                }
            }
        });
    }*/

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