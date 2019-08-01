package com.yhw.common.security.model;

import lombok.Data;

/**
 * 远程 token 配置
 * 通过配置文件读取
 * Created by YHW on 2019/8/2.
 */
@Data
public class RemoteTokenProperties {
    private String client_id;
    private String client_secret;
    private String check_token_uri;
}
