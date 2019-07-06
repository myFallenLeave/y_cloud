package com.yhw;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;

import java.util.Base64;

/**
 * Created by YHW on 2019/6/2.
 */
public class Test {
    static JSONObject parameter = new JSONObject();

    public static String url = "localhost:9999";


    @org.junit.Test
    public void getToken(){
        JSONObject tokenParameters = new JSONObject();
        //使用路由请求token
        url = url.concat("/abc/oauth/token");

        tokenParameters.put("username","user1");
        tokenParameters.put("password","123456");
        tokenParameters.put("scope","select");
        tokenParameters.put("grant_type","password");


        String result = HttpRequest.post(url)
                .form(tokenParameters)
                .header("Authorization","Basic ".concat(Base64.getEncoder().encodeToString("test:123456".getBytes())))
                .execute()
                .body();
        System.out.println(result);
    }


    //配置：spring.cloud.gateway.discovery.locator.enabled=true
    //启用基于服务发现的路由定位,设为true便开启通过服务中心的自动根据 serviceId 创建路由的功能
    @org.junit.Test
    public void test(){
        JSONObject tokenParameters = new JSONObject();
        //使用路由请求token
        url = url.concat("/auth-example/oauth/token");

        tokenParameters.put("username","user1");
        tokenParameters.put("password","123456");
        tokenParameters.put("scope","select");
        tokenParameters.put("grant_type","password");


        String result = HttpRequest.post(url)
                .form(tokenParameters)
                .header("Authorization","Basic ".concat(Base64.getEncoder().encodeToString("test:123456".getBytes())))
                .execute()
                .body();
        System.out.println(result);
    }
}
