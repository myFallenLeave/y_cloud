package com.yhw.auth.server.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 认证服务器配置
 */
@Configuration
@EnableAuthorizationServer
@AllArgsConstructor
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final RedisConnectionFactory connectionFactory;
    private final DataSource dataSource;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    @Bean
    public TokenStore tokenStore(){
        TokenStore tokenStore = new RedisTokenStore(connectionFactory);
        return tokenStore;
    }


    //在令牌端点上定义了安全约束
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer){
        //允许用户通过表单来验证
        oauthServer.checkTokenAccess("permitAll()");
        //和基于内存的形式不一样，此处显示指定了密码加密方式,所以存储的DB的client_secret 不需要加{bcrypt} 前缀
        oauthServer.passwordEncoder(new BCryptPasswordEncoder());
//        oauthServer.passwordEncoder(NoOpPasswordEncoder.getInstance());//客户端密码不需要加密
        oauthServer.allowFormAuthenticationForClients();
    }

    /**
     * 使用jdbc
     * @return
     */
    @Bean
    public ClientDetailsService clientDetails(){
        return new JdbcClientDetailsService(dataSource);
    }

    //定义了客户端细节服务
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //持久化客户端信息
        clients.withClientDetails(clientDetails());
    }

    //定义了授权和令牌端点和令牌服务
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints){
        endpoints.allowedTokenEndpointRequestMethods(HttpMethod.GET,HttpMethod.POST)
                .tokenStore(tokenStore())//持久化方式
                .tokenEnhancer(tokenEnhancer())//token中添加的额外信息
                .userDetailsService(userDetailsService)//用户实现
                .authenticationManager(authenticationManager)//令牌管理
                .reuseRefreshTokens(false)//重新刷新令牌
                .exceptionTranslator(null);

        /*DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(endpoints.getTokenStore());
        tokenServices.setSupportRefreshToken(false);
        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
        tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30));

        endpoints.authenticationManager(authenticationManager)//令牌管理
        .userDetailsService(userDetailsService)
        .tokenServices(tokenServices).exceptionTranslator(null);*/
    }

    @Bean
    public TokenEnhancer tokenEnhancer(){
        return ((accessToken, authentication) -> {
            final Map<String, Object> additionalInfo = new HashMap<>(1);
            additionalInfo.put("license", "made by yunyang");
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        });
    }

}
