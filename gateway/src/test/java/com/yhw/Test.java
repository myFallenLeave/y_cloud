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
}
