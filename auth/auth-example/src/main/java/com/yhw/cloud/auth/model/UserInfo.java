package com.yhw.cloud.auth.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;

@NoArgsConstructor
@Data
public class UserInfo {

    private String id;

    private String userName;

    private String password;

    public UserInfo(String id,String userName,String password){
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

}
