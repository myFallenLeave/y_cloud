package com.yhw.cloud.auth;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Base64;

public class UserTest {

    static JSONObject parameter = new JSONObject();

    public static String url = "localhost:8888";

    //方式1:通过直接传递 client_id+client_secret方式请求token
    @Test
    public void getToken(){
        JSONObject tokenParameters = new JSONObject();
        url = url.concat("/oauth/token");
        tokenParameters.put("username","user1");
        tokenParameters.put("password","123456");
        tokenParameters.put("scope","select");
        tokenParameters.put("grant_type","password");
        tokenParameters.put("client_id","test");
        tokenParameters.put("client_secret","123456");//客户端密码需要加密



        String result = HttpRequest.post(url)
                .form(tokenParameters)
                .execute()
                .body();
        System.out.println(result);
    }

    //方式2 通过header Authorization 加密的方式请求token
    //加密方式  String str1 = client_id:client_secret 转base64
    //Authorization = Basic str1
    @Test
    public void getToken1(){
        JSONObject tokenParameters = new JSONObject();
        url = url.concat("/oauth/token");
        tokenParameters.put("username","user1");
        tokenParameters.put("password","123456");
        tokenParameters.put("scope","select");
        tokenParameters.put("grant_type","password");

        ;

        String result = HttpRequest.post(url)
                .form(tokenParameters)
                .header("Authorization","Basic ".concat(Base64.getEncoder().encodeToString("test:123456".getBytes())))
                .execute()
                .body();
        System.out.println(result);
    }

    @Test
    public void order(){
        url = url.concat("/order/1");

        //        "access_token":"6234abf9-0765-4119-ac44-a2ae1eed6dd8","token_type":"bearer",

        //1.通过from 方式传递token
//        parameter.put("access_token","6234abf9-0765-4119-ac44-a2ae1eed6dd8");
        String result = HttpRequest.get(url)
                .form(parameter)
                //2.通过header方式请求
                .header("Authorization","bearer 6234abf9-0765-4119-ac44-a2ae1eed6dd8")
                .execute()
                .body();
        System.out.println(result);
    }

    @Test
    public void product(){
        url = url.concat("/product/1");

        String result = HttpRequest.get(url)
                .form(parameter)
                .header("Authorization","bearer 6234abf9-0765-4119-ac44-a2ae1eed6dd8")
                .execute()
                .body();
        System.out.println(result);
    }

    public static void main(String[] args){
         System.out.println(new BCryptPasswordEncoder().encode("123456"));

    }
}
