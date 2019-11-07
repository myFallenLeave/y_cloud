package com.yhw.im.netty.example.config;

import com.yhw.im.netty.example.process.SimpleAuthProcess;
import com.yhw.netty.websocket.NettyWebSocketStarter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author yhw
 */
@Configuration
public class ExampleConfig {

    @Autowired
    private SimpleAuthProcess authProcess;


    @Bean
    public NettyWebSocketStarter nettyWebSocketStarter(){
        return new NettyWebSocketStarter(authProcess);
    }
}
