package com.yhw.netty.websocket;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ClassUtil;
import com.yhw.netty.websocket.config.ImPropertiesConfig;
import com.yhw.netty.websocket.constants.NtConstant;
import com.yhw.netty.websocket.context.MessageManager;
import com.yhw.netty.websocket.context.ProcessManager;
import com.yhw.netty.websocket.packets.Message;
import com.yhw.netty.websocket.process.AuthProcess;
import com.yhw.netty.websocket.process.CmdProcess;
import com.yhw.netty.websocket.server.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author yhw
 */
@ComponentScan(basePackages = {NtConstant.IM_NETTY_PACKAGE_PATH})
@Slf4j
public class NettyWebSocketStarter {

    private int port;

    private String websocketPath;

    /**
     * 是否启用ssl
     */
    private boolean ssl = false;

    private WebSocketServer server;

    @Autowired
    private AuthProcess authProcess;

    @Autowired
    private ImPropertiesConfig propertiesConfig;


    @Autowired
    private ApplicationContext applicationContext;


    @PostConstruct
    public void init(){
        setAttributes(propertiesConfig);
        initCmdProcess();
        server = new WebSocketServer(port,websocketPath,ssl,authProcess);
        server.start();
    }

    private void setAttributes(ImPropertiesConfig propertiesConfig){
        String packetScanPath = "";
        if(propertiesConfig == null){
            log.error(">>>>>>>> im config prefix: im.ws ");
            log.error(">>>>>>>> im config attributes can not by empty ");
            System.exit(0);
        }

        this.port = propertiesConfig.getPort();
        this.websocketPath = propertiesConfig.getWebsocketPath();
        if(websocketPath == null || websocketPath.isEmpty()){
            websocketPath = NtConstant.DEFAULT_WEBSOCKET_PATH;
        }

        this.ssl = propertiesConfig.isSsl();
        packetScanPath = propertiesConfig.getPacketScan();
        if(packetScanPath == null || packetScanPath.isEmpty()){
            log.error(">>>>>>>> im.ws.packetScan can not by empty ");
            System.exit(0);
        }
        initPacket(packetScanPath);

    }

    private void initCmdProcess(){
        try{
            List<CmdProcess> list = new ArrayList<>();
            String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();

            Stream.of(beanDefinitionNames).forEach(beanName -> {
                Object bean = applicationContext.getBean(beanName);
                if(bean instanceof CmdProcess){
                    list.add((CmdProcess)bean);
                }
            });

            ProcessManager.getInstance().addCmdProcesss(list);
            log.info("init im process repository success ! count [" + list.size() + "]");
        }catch (Exception e){
            log.error("nit im process repository error",e);
            System.exit(0);
        }
    }


    private void initPacket(String packetScanPath){
        try{
            Set<Class<?>> set = ClassUtil.scanPackage(packetScanPath);
            Object obj = null;
            List<Message> list = new ArrayList<>();
            if(CollectionUtil.isNotEmpty(set)){
                for (Class clazz : set) {
                    obj = clazz.newInstance();
                    if(obj instanceof Message){
                        list.add((Message) obj);
                    }
                }
            }

            MessageManager.getInstance().addMessages(list);
            log.info("init IM packet repository success ! count [" + list.size() + "]");
        }catch (Exception e){
            log.error("init packet repository error",e);
            System.exit(0);
        }
    }




}
