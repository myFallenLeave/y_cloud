package com.yhw.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * Created by YHW on 2019/6/2.
 */
@Api(value = "测试")
@RestController
public class TestController {

    @GetMapping("/getTest")
    public String test(){
        System.out.println("哈哈");
        if(true){
            System.out.println("aaaaaaaaaaaaaaaa111111111a");
        }
        return "get test";
    }

    @GetMapping("/getTest/abc")
    public String test1(){
        System.out.println("get test,abc");
        return "get test,abc";
    }

    @PostMapping("/postTest")
    public String postTest(){
        return "post test";
    }
}
