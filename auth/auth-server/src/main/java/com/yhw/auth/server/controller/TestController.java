package com.yhw.auth.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

//@RequestMapping("/test")
@RestController
public class TestController {

    @GetMapping("/aaa")
    public String test(){
        return "test";
    }

    @RequestMapping("/bbbb")
    public String test1(){
        return "test1";
    }

    @GetMapping("/user")
    public Principal user(Principal user){
        System.out.println("111111111afsafasfaaaaaa");
        return user;
    }
}
