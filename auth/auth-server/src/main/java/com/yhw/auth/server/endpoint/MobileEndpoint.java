package com.yhw.auth.server.endpoint;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.yhw.common.model.AuthUser;
import io.lettuce.core.internal.LettuceLists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/***
 * 通过手机号码获取token
 * @author yhw
 * @date 2019/09/02
 */
@Api(value = "手机号码获取token控制器")
@Slf4j
@Controller
public class MobileEndpoint{


    @Resource
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Resource
    private ClientDetailsService clientDetailsService;

    @Value("${security.oauth2.resource.clientId}")
    private String clientId;

    //注入远程服务类，通过手机号码获取用户信息（此处为模拟假数据）



    @ApiOperation(value = "通过手机号码，获取token令牌")
    @GetMapping(value = "/oauth/mobile/{mobile}")
    public ResponseEntity<OAuth2AccessToken> getAccessToken(@PathVariable("mobile") String mobile) throws HttpRequestMethodNotSupportedException {
        return postAccessToken(mobile);
    }

    @ApiOperation(value = "通过手机号码，获取token令牌，content-type:application/x-www-form-urlencoded")
    @PostMapping(value = "/oauth/mobile")
    public ResponseEntity<OAuth2AccessToken> postAccessToken(@RequestParam("mobile") String mobile){

        //校验验证码，如果验证错误，则直接返回，抛出验证码错误


        Assert.isFalse(StrUtil.isEmpty(clientId),"client Id isEmpty. please setting clientId parameter !");

        ClientDetails authenticatedClient = clientDetailsService.loadClientByClientId(clientId);

        TokenRequest tokenRequest = new TokenRequest(CollectionUtil.newHashMap(), clientId, LettuceLists.newList("app"), "password");


        if (clientId != null && !clientId.equals("")) {
            // Only validate the client details if a client authenticated during this
            // request.
            if (!clientId.equals(tokenRequest.getClientId())) {
                // double check to make sure that the client ID in the token request is the same as that in the
                // authenticated client
                throw new InvalidClientException("Given client ID does not match authenticated client");
            }
        }

        if (!StringUtils.hasText(tokenRequest.getGrantType())) {
            throw new InvalidRequestException("Missing grant type");
        }
        if (tokenRequest.getGrantType().equals("implicit")) {
            throw new InvalidGrantException("Implicit grant type not supported from token endpoint");
        }


        OAuth2AccessToken accessToken = authorizationServerTokenServices.createAccessToken(getOAuth2Authentication(authenticatedClient, tokenRequest));

        if (accessToken == null) {
            throw new UnsupportedGrantTypeException("Unsupported grant type: " + tokenRequest.getGrantType());
        }

        return getResponse(accessToken);

    }

    /**
     * 获取oauth认证信息
     * @param client 客户端信息
     * @param tokenRequest token请求信息
     * @return oauth认证信息
     */
    private OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        //客户端信息
        OAuth2Request storedOAuth2Request = new  OAuth2Request(tokenRequest.getRequestParameters(), client.getClientId(), client.getAuthorities(), true,client.getScope() ,
                client.getResourceIds(), null, null, null);

        Authentication userAuthentication = getUserAuthentication();
        if(userAuthentication == null){
            throw new InvalidGrantException("current mobile not exists");
        }

        return new OAuth2Authentication(storedOAuth2Request, userAuthentication);
    }


    private Authentication getUserAuthentication(){
        //====================设置用户信息(此处的信息，和使用/oauth/token的信息需要保持一致，此处可以通过远程调用得到)=======================
        List<GrantedAuthority> authorities = new ArrayList<>(5);
        authorities.add(new SimpleGrantedAuthority("ROLE_normal"));
        authorities.add(new SimpleGrantedAuthority("wanglaowu"));
        AuthUser authUser = new AuthUser("123456789", "wanglaowu", "",
                true, true, true, true, authorities);

        //用户认证信息
        return new UsernamePasswordAuthenticationToken(authUser,null,authUser.getAuthorities());
    }


    private ResponseEntity<OAuth2AccessToken> getResponse(OAuth2AccessToken accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");
        headers.set("Content-Type", "application/json;charset=UTF-8");
        return new ResponseEntity<OAuth2AccessToken>(accessToken, headers, HttpStatus.OK);
    }

    private boolean isRefreshTokenRequest(Map<String, String> parameters) {
        return "refresh_token".equals(parameters.get("grant_type")) && parameters.get("refresh_token") != null;
    }

    private boolean isAuthCodeRequest(Map<String, String> parameters) {
        return "authorization_code".equals(parameters.get("grant_type")) && parameters.get("code") != null;
    }

}
