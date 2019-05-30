package com.yhw.cloud.auth.controller;

import com.yhw.cloud.auth.model.MyUserDetails;
import com.yhw.cloud.auth.model.UserInfo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping("/")
@RestController
public class UserController {
    private final TokenStore tokenStore;

    @PostMapping("/bar")
    public String bar(@RequestHeader("Authorization") String auth){
        MyUserDetails userDetails = (MyUserDetails)tokenStore.readAuthentication(auth.split(" ")[1]).getPrincipal();

        UserInfo userInfo = userDetails.getUserInfo();
        return userInfo.getUserName().concat(":").concat(userInfo.getPassword());
    }


    @GetMapping("/product/{id}")
    public String getProduct(@PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "product id : " + id;
    }

    @GetMapping("/order/{id}")
    public String getOrder(@PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "order id : " + id;
    }
}
