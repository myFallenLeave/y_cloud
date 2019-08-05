package com.yhw.common.security;

import com.yhw.common.model.AuthUser;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    private SecurityUtil(){}


    public static AuthUser getUser(){
        AuthUser authUser = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof  AuthUser){
            authUser =   (AuthUser)principal;
        }
        return authUser;
    }
}
