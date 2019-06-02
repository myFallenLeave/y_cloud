package com.yhw.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

/**
 * 自定义filter
 * 实现GatewayFilterFactory接口。
 * 类名一定要为filterName + GatewayFilterFactory，如这里定义为AuthCheckGatewayFilterFactory的话，它的filterName就是AuthCheck
 * 需要使用当前filter，加入路由即可
 * Created by YHW on 2019/6/2.
 */
@Component
public class AuthCheckGatewayFilterFactory extends AbstractGatewayFilterFactory<Object>{

    //存在问题，进不来
    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            System.out.println("hello welcome");
            if (hasAuth(exchange)) {
                return chain.filter(exchange);
            } else {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                response.getHeaders().add(HttpHeaders.WWW_AUTHENTICATE, "Basic Realm=\"cloud\"");
                return response.setComplete();
            }
        };
    }

    /**
     * 简单的basic认证
     *
     * @param exchange 上下文
     * @return 是否有权限
     */
    private boolean hasAuth(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String auth = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
//        log.info("Basic认证信息为：{}", auth);
        System.out.println("hello welcome"+auth);
        return true;
    }
}
