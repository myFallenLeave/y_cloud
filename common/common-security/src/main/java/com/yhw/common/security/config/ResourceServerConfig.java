package com.yhw.common.security.config;

import com.yhw.common.constants.SecurityConstant;
import com.yhw.common.security.model.RemoteTokenProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

/**
 * 资源服务(每一个服务相当于一个资源服务器)
 * 此处只配置资源服务
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Bean
    @ConfigurationProperties(prefix = "spring.security.oauth2.client")
    public RemoteTokenProperties remoteTokenProperties() {
        return new RemoteTokenProperties();
    }

    @Bean
    public AccessTokenConverter accessTokenConverter() {
        return new DefaultAccessTokenConverter();
    }

    @Bean
    public ResourceServerTokenServices remoteTokenServices(){
        RemoteTokenServices remoteTokenServices = new RemoteTokenServices();

        RemoteTokenProperties properties = remoteTokenProperties();
        remoteTokenServices.setCheckTokenEndpointUrl(properties.getCheck_token_uri());
        remoteTokenServices.setClientId(properties.getClient_id());
        remoteTokenServices.setClientSecret(properties.getClient_secret());
        //use default converter token
        remoteTokenServices.setAccessTokenConverter(accessTokenConverter());
        return remoteTokenServices;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenServices(remoteTokenServices())
                .resourceId(SecurityConstant.RESOURCE_CLIENT)
                .stateless(true);//资源都需要token认证
    }

    //采取spring security 进行权限过滤


    /**
     * 配置资源服务器权限拦截
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        //允许使用iframe 嵌套，避免swagger-ui 不被加载的问题
        http.headers().frameOptions().disable();
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>
                .ExpressionInterceptUrlRegistry registry = http
                .authorizeRequests();


        registry.antMatchers("/actuator/**","/v2/api-docs","/user/info/*","/log/save",
                //放行 swagger
                "/swagger-resources/configuration/ui",
                "/swagger-resources","/swagger-resources/configuration/security",
                "/swagger-ui.html","/css/**", "/js/**","/images/**", "/webjars/**", "**/favicon.ico", "/index").permitAll();
        registry.anyRequest().authenticated()
                .and().csrf().disable();
        super.configure(http);
    }
}
