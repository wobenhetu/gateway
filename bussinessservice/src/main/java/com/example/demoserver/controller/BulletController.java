package com.example.demoserver.controller;

import com.example.demoserver.entity.BulletMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/*
* 前端是通过SockJS来建立WebSocket连接的；
*  SockJS的作用是让WEB应用使用WebSocket API，但是在必要的运行时能改变成非WebSocket（比如HTTP的长连接）而无需改动代码。
* SockJS被设计成在浏览器中使用；
* SockJS可用的传输类型有三种：WebSocket、HTTP Streaming、HTTP长轮询；
* 注意：SockJS客户端开始时会发送一个GET类型的"/info"请求从服务器去获取基本信息，这个请求之后SockJS必须决定使用哪种传输，
* 可能是WebSocket，如果不是的话，在大部分浏览器中会使用HTTP Streaming或者HTTP长轮询。
*
* */


@RestController
public class BulletController {

    private static final Logger logger=LoggerFactory.getLogger(BulletController.class);

    @MessageMapping("/sendto")
    //SendTo 发送至 Broker 下的指定订阅路径
    @SendTo("/toAll/bulletScreen")
    public String say(BulletMessageDTO clientMessage) {
        //方法用于广播测试
        if (clientMessage!=null){
            if (clientMessage.getMessage()!=null){
                clientMessage.setMessage(clientMessage.getMessage().trim());
            }
        }
        logger.info(clientMessage.getUsername()+":"+clientMessage.getMessage());
        return clientMessage.getMessage();
    }



}