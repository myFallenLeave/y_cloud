package com.yhw.auth.server.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import sun.security.util.SecurityConstants;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 认证服务器配置
 */
@AllArgsConstructor
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final RedisConnectionFactory connectionFactory;
    private final DataSource dataSource;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    //资源服务器配置  ResourceServerConfigurerAdapter(目前不需要实现)

    //在令牌端点上定义了安全约束
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer){
        //允许用户通过表单来验证
        oauthServer.allowFormAuthenticationForClients()
                .checkTokenAccess("permitAll()");
    }

    //定义了客户端细节服务
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //持久化客户端信息
        JdbcClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        clientDetailsService.setSelectClientDetailsSql(" where client_id = ? ");
        clientDetailsService.setFindClientDetailsSql(" order by client_id ");
//        clientDetailsService.setSelectClientDetailsSql(SecurityConstants.DEFAULT_SELECT_STATEMENT);
//        clientDetailsService.setFindClientDetailsSql(SecurityConstants.DEFAULT_FIND_STATEMENT);
        clients.withClientDetails(clientDetailsService);
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
    }

    @Bean
    public TokenStore tokenStore(){
        TokenStore tokenStore = new RedisTokenStore(connectionFactory);
        return tokenStore;
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
