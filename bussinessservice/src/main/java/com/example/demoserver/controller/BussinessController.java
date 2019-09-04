package com.example.demoserver.controller;


import org.springframework.web.bind.annotation.*;

//@CrossOrigin
@RestController
@RequestMapping("/getbussiness")
public class BussinessController {

    @GetMapping("/getinfo")
    public String getBussinessInfo(/*@RequestParam(value = "username",required = false) String username*/){
        String returnString = "<h1>"+"hello world!!!!!!!!!!!!!!!!"+"</h1>";
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
