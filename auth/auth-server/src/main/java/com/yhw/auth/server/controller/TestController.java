package com.yhw.auth.server.controller;

import com.yhw.common.model.AuthUser;
import com.yhw.common.security.SecurityUtil;
import com.yhw.service.api.user.feign.TestRemoteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

//@RequestMapping("/test")
@AllArgsConstructor
@RestController
public class TestController {

    private final TestRemoteService testRemoteService;

    @GetMapping("/aaa")
    public String test(){
        //需要解决 远程调用 token 认证的问题
//        System.out.println(testRemoteService.test());
        AuthUser authUser = SecurityUtil.getUser();
        if(authUser != null){
            System.out.println(authUser.getUserId());
        }
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
