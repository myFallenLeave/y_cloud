package com.yhw.cloud.auth.service.impl;

import com.yhw.cloud.auth.model.MyUserDetails;
import com.yhw.cloud.auth.model.UserInfo;
import com.yhw.cloud.auth.service.UserService;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Primary
@Service("userService")
public class UserServiceImpl implements UserService {


    private final static Set<UserInfo> users = new HashSet<>();

    static {
        //spring security5 新增多种加密方式
        users.add(new UserInfo("1","user1",new BCryptPasswordEncoder().encode("123456")));
        users.add(new UserInfo("2","user2",new BCryptPasswordEncoder().encode("234567")));
        users.add(new UserInfo("3","user3",new BCryptPasswordEncoder().encode("345678")));
        users.add(new UserInfo("4","user4",new BCryptPasswordEncoder().encode("456789")));

    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<UserInfo> user = users.stream().filter((u)->
            u.getUserName().equals(s)
        ).findFirst();
        if(!user.isPresent()){
            throw new RuntimeException("登录账号不存在");
        }
        return new MyUserDetails(user.get());
    }
}
