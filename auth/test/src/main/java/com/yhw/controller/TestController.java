package com.yhw.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * Created by YHW on 2019/6/2.
 */
@RestController
public class TestController {

    @GetMapping("/getTest")
    public String test(){
        return "get test";
    }

    @PostMapping("/postTest")
    public String postTest(){
        return "post test";
    }
}
