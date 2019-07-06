package com.yhw.cloud.config;

import cn.hutool.core.util.StrUtil;
import com.yhw.cloud.ws.listener.ExampleWsServerAioListener;
import com.yhw.tio.websocket.MyWebsocketStarter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by YHW on 2019/6/22.
 */
@Configuration
public class ImWebSocketConfig {

    @Value("${im.ws.port}")
    private Integer webSocketPort;

    @Value("${im.ws.scan}")
    private String cmdScan;


    @Bean(initMethod = "start")
    public MyWebsocketStarter websocketStarter(){
        MyWebsocketStarter websocketStarter = new MyWebsocketStarter();
        //设置包扫描路径
        if(StrUtil.isBlank(cmdScan)){
            cmdScan = "com.yhw.cloud.ws";
        }
        //设置包扫描路径
        websocketStarter.setCmdScanPakage(cmdScan);
        //设置端口
        websocketStarter.setPort(webSocketPort);
        //设置监听
        websocketStarter.setServerAioListener(new ExampleWsServerAioListener());
//        websocketStarter.setWsMsgHandler();
        return websocketStarter;
    }
}
