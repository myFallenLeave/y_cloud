package com.yhw.cloud.auth.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Data
public class MyUserDetails extends User {

    private UserInfo userInfo;

    public MyUserDetails(UserInfo userInfo) {
        super(userInfo.getUserName(), userInfo.getPassword(), true,true,true,true, Collections.EMPTY_SET);
        this.userInfo = userInfo;
    }
}
