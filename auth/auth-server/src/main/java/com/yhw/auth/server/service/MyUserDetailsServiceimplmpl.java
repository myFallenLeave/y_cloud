package com.yhw.auth.server.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class MyUserDetailsServiceimplmpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //通过feign调用，使用缓存
        return getUser();
    }


    private UserDetails getUser(){
        List<GrantedAuthority> authorities = new ArrayList<>(5);

        /*for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }*/
        return new MyUser("", "", "user.getUsername()", "{bcrypt}" + "user.getPassword()",
              true, true, true, true, authorities);
    }


}
