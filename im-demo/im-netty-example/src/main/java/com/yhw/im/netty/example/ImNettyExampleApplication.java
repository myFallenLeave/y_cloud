package com.yhw.im.netty.example;

import com.yhw.netty.websocket.NettyWebSocketStarter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(NettyWebSocketStarter.class)
@SpringBootApplication
public class ImNettyExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImNettyExampleApplication.class, args);
    }

}
