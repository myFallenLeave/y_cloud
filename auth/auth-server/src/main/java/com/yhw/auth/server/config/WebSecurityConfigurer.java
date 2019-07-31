package com.yhw.auth.server.config;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Primary
@Order(90)
@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {


    @Override
    @SneakyThrows
    protected void configure(HttpSecurity http) {
        http.authorizeRequests()//配置路径拦截，表明路径访问所对应的权限，角色，认证信息
                .antMatchers("/actuator/**", "/token/**","/aaa")
                .permitAll()//任何人都可以访问上述URL
                .anyRequest()
                .authenticated()//必须要经过认证的才能访问
                .and()
                .csrf()//允许跨域
                .disable();

    }

    @Bean
    @Override
    @SneakyThrows
    public AuthenticationManager authenticationManagerBean() {
        return super.authenticationManagerBean();
    }
}
