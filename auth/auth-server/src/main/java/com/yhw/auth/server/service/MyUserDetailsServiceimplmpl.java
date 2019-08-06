package com.yhw.auth.server.service;

import com.yhw.common.model.AuthUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MyUserDetailsServiceimplmpl implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //通过feign调用，使用缓存
        return getUser(username);
    }


    //使用模拟数据
    private UserDetails getUser(String username){
        List<GrantedAuthority> authorities = null;
        String password = new BCryptPasswordEncoder().encode("123456");
        if("zhangsan".equals(username)){
            authorities = new ArrayList<>(5);
            authorities.add(new SimpleGrantedAuthority("ROLE_normal"));
            //密码一定要加密

            return new AuthUser("123456", "zhangsan", "{bcrypt}" + password,
                    true, true, true, true, authorities);
        }

        //admin
        authorities = new ArrayList<>(5);
        authorities.add(new SimpleGrantedAuthority("ROLE_admin"));
        return new AuthUser("12345678", "admin", "{bcrypt}" + password,
                true, true, true, true, authorities);
    }

}
