package com.yhw.common.security;

import com.yhw.common.model.AuthUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    private SecurityUtil() {
    }

    /**
     * 获取当前用户UserId
     * @return
     */
    public static String getCurrentUserId(){
        AuthUser authUser = getUser();
        if(authUser == null){
            return "";
        }
        return authUser.getUserId();
    }


    public static AuthUser getUser() {
        AuthUser authUser = null;
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return authUser;
        }
        Object principal = authentication.getPrincipal();
        if (principal == null) {
            return authUser;
        }
        if (principal instanceof AuthUser) {
            authUser = (AuthUser) principal;
        }
        return authUser;
    }

    public static Authentication getAuthentication() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext == null) {
            return null;
        }
        return securityContext.getAuthentication();
    }
}
