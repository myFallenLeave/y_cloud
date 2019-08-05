package com.yhw.auth.server.service;

import com.yhw.common.model.AuthUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsServiceimplmpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //通过feign调用，使用缓存
        return getUser();
    }


    //使用模拟数据
    private UserDetails getUser(){
        List<GrantedAuthority> authorities = new ArrayList<>(5);

        /*for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }*/


        //密码一定要加密
        String password = new BCryptPasswordEncoder().encode("123456");
        return new AuthUser("123456", "zhangsan", "{bcrypt}" + password,
              true, true, true, true, authorities);
    }


}
