package com.yhw.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * 自定义filter
 * 实现GatewayFilterFactory接口。
 * 类名一定要为filterName + GatewayFilterFactory，如这里定义为AuthCheckGatewayFilterFactory的话，它的filterName就是AuthCheck
 * 需要使用当前filter，加入路由即可
 * Created by YHW on 2019/6/2.
 */
@Component
public class AuthCheckGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthCheckGatewayFilterFactory.Config> {

    public static final String STATUS_KEY = "status";

    //在类的构造器中一定要调用下父类的构造器把Config类型传过去
    public AuthCheckGatewayFilterFactory() {
        super(Config.class);
    }

    //如果需要接受参数，必须重写
    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(STATUS_KEY);
    }

    @Override
    public GatewayFilter apply(Config config) {
        //可以进入当前方法,却无法进入 匿名类
        return (exchange, chain) ->  {
            System.out.println(exchange.getLogPrefix());
            if (hasAuth(exchange)) {
                return chain.filter(exchange);
            }
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.getHeaders().add(HttpHeaders.WWW_AUTHENTICATE, "Basic Realm=\"cloud\"");
            return response.setComplete();
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

    public static class Config{
        private boolean status;

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }
    }
}
