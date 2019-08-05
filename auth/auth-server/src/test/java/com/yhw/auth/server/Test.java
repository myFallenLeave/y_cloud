package com.yhw.auth.server;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Base64;

public class Test {

    static JSONObject parameter = new JSONObject();

    public static String url = "http://localhost:8888";

    /**
     * 测试访问其他服务器
     * 不传递token 将会出现401错误
     * 通过token服务器拿到 token 就可以调用成功了
     */
    @org.junit.Test
    public void getTest(){
        String url1 = "localhost:8080/getTest";

        String result = HttpRequest.get(url1)
                .header("Authorization","bearer fb57c8ee-6629-4266-a41b-15a92c614bfc")
                .execute()
                .body();
        System.out.println("------------"+result);
    }

    /**
     * 可以通过 不传递Authorization 测试
     */
    @org.junit.Test
    public void aaa(){
        url = url.concat("/aaa");

//        String result = HttpUtil.post(url); bearer  Basic
        String result = HttpRequest.get(url)
                .header("Authorization","bearer 24d1b36d-78ef-4015-9e5a-edfd27420327")
                .execute()
                .body();
        System.out.println(result);
    }

    //方式1:通过直接传递 client_id+client_secret方式请求token
    @org.junit.Test
    public void getToken(){
        JSONObject tokenParameters = new JSONObject();
        url = url.concat("/oauth/token");
        tokenParameters.put("username","zhangsan");
        tokenParameters.put("password","123456");
        tokenParameters.put("scope","app");
        tokenParameters.put("grant_type","password");
        tokenParameters.put("client_id","client");
        tokenParameters.put("client_secret","secret");//数据库存储时  不需要指定{} 直接存储加密生成的字符即可
//        ["password","refresh_token"]

        String result = HttpRequest.post(url)
                .form(tokenParameters)
                .execute()
                .body();
        System.out.println(result);
    }

    //方式2 通过header Authorization 加密的方式请求token
    //加密方式  String str1 = client_id:client_secret 转base64
    //Authorization = Basic str1
//    @org.junit.Test
    public void getToken1(){
        JSONObject tokenParameters = new JSONObject();
        url = url.concat("/oauth/token");
        tokenParameters.put("username","zhangsan");
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

    //认证token 会返回token所属的用户信息
    @org.junit.Test
    public void checkToken(){
        url = url.concat("/oauth/check_token");

        parameter.put("token","24d1b36d-78ef-4015-9e5a-edfd27420327");

        String result = HttpRequest.post(url)
                .form(parameter)
                .execute()
                .body();
        System.out.println(result);
    }


    public static void main(String[] args){
        String aa = (new BCryptPasswordEncoder().encode("secret"));
        System.out.println(aa);
        //{bcrypt}$2a$10$BslJHW/QbFJfNZ1XbSdwV.6U7Sv4k4wemaaMZWAWoDVwza/MH
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean flag = bCryptPasswordEncoder.matches("123456",aa);
        System.out.println(flag);

    }
}
