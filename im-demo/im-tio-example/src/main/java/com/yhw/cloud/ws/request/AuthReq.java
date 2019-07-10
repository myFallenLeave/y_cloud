package com.yhw.cloud.ws.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 通过约定好的请求编码，对text文件进行序列化成java对象
 * Created by YHW on 2019/6/22.
 */
@Data
public class AuthReq implements Serializable{

    private String token;

    private String userName;

    private String password;
}
