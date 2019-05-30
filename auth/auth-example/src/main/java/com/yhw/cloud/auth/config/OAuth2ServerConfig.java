package com.yhw.cloud.auth.config;

import com.yhw.cloud.auth.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
public class OAuth2ServerConfig {

    private static final String DEMO_RESOURCE_ID = "order";


    //ResourceServerConfigurerAdapter 默认情况下是spring security oauth2的http配置
    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter{
        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//            super.configure(resources);
            //设置资源ID  配置客户端必须也带此ID
            resources.resourceId(DEMO_RESOURCE_ID).stateless(true);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .and()
                    .anonymous()
                    .and()
                    //验证所有请求
                    .authorizeRequests()
                    //access通过security表示式设置权限控制
                    // 访问受保护资源 /product/** 的要求：客户端 Scope 为 select，拥有delete权限
                    .antMatchers("/product/**").access("#oauth2.hasScope('select') and hasPermission('delete')")
                    //配置order访问控制，必须认证后才能访问
                    .antMatchers("/order/**").authenticated();
        }
    }

    @AllArgsConstructor
    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter{
        private final AuthenticationManager authenticationManagerBean;
        //        private final  RedisConnectionFactory redisConnectionFactory;
        private final UserService userService;

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            //客户端访问模式，客户端模式(Client_credentials)、密码模式(password)、授权码模式(authorization_code)
            clients.inMemory()
                    .withClient("test")//客户端ID
                    .resourceIds(DEMO_RESOURCE_ID)//
                    .authorizedGrantTypes("password","refresh_token")//
                    .scopes("select")//客户端域，限制客户端的访问范围
                    .authorities("oauth2")//
                    //secret 必须进行加密
                    .secret("{".concat("bcrypt").concat("}").concat(new BCryptPasswordEncoder().encode("123456")))//客户端的安全码，相当于password
                    .accessTokenValiditySeconds(3600)//token过期时间 1 hour
                    .refreshTokenValiditySeconds(60*60*24*30);//refresh过期时间 1 month
            //配置client认证客户端
//                    .and().withClient("client_1")
//                    .resourceIds(DEMO_RESOURCE_ID)
//                    .authorizedGrantTypes("client_credentials")
//                    .scopes("select")
//                    .authorities("oauth2")
//                    .secret("123456"
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//            super.configure(endpoints);
            endpoints
//                    .tokenStore(new RedisTokenStore(redisConnectionFactory))
                    //使用内存的tokenStore
//                    .tokenStore(new InMemoryTokenStore())
                    .tokenStore(tokenStore())
                    .authenticationManager(authenticationManagerBean)
                    //配置userService 这样每次认证的时候会去检验用户是否锁定，有效等
                    .userDetailsService(userService)
                    //增加配置，允许 GET、POST 请求获取 token，即访问端点：oauth/token
                    .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);

            endpoints.reuseRefreshTokens(true);
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//            super.configure(security);
            //允许表单验证
            security.allowFormAuthenticationForClients();
        }

        @Bean
        public TokenStore tokenStore(){
            return new InMemoryTokenStore();
        }

    }


    @AllArgsConstructor
    @Configuration
    @Order(-20)
    protected static class AuthenticationManagerConfiguration extends GlobalAuthenticationConfigurerAdapter {


        private final UserDetailsService userDetailsService;
        //设置加密方式 用户密码需要使用当前加密方式进行加密
        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService)
                    .passwordEncoder(new BCryptPasswordEncoder());
        }

    }

}
