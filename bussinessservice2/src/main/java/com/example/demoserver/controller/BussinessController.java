package com.example.demoserver.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/getbussiness")
public class BussinessController {

    @GetMapping("/getinfo")
    public String getBussinessInfo(){
        String returnString = "<h1>"+"中国文化博大精深！！！！"+"</h1>";
        System.out.println(returnString);
        return returnString;
    }


    @GetMapping("/login")
    public String login(){
        String returnString = "<h1>"+"登陆需要token！！！！"+"</h1>";
        return returnString;
    }


    @GetMapping("/logout")
    public String logout(){
        String returnString = "<h1>"+"登出不需要token！！！！"+"</h1>";
        return returnString;
    }

}
