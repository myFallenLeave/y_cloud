package com.yhw.tio.websocket;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ClassUtil;
import com.yhw.tio.websocket.cmd.CmdProcess;
import com.yhw.tio.websocket.command.CommandManager;
import com.yhw.tio.websocket.handler.MyWsMsgHandler;
import com.yhw.tio.websocket.listener.MyWsServerAioListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.server.ServerGroupContext;
import org.tio.server.intf.ServerAioListener;
import org.tio.websocket.server.WsServerStarter;
import org.tio.websocket.server.handler.IWsMsgHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * ws服务启动类
 * 官方示例 https://gitee.com/tywo45/tio-websocket-showcase
 */
public class MyWebsocketStarter {

    private static Logger logger = LoggerFactory.getLogger(MyWebsocketStarter.class);

    //启动类
    private WsServerStarter wsServerStarter;

    //上下文组
    private ServerGroupContext serverGroupContext;

    //回调
    private IWsMsgHandler wsMsgHandler;

    /**
     * 包扫描路径
     */
    private String cmdScanPakage;

    /**
     * 通过继承监听
     */
    private ServerAioListener serverAioListener;

    private Integer port;

    public MyWebsocketStarter(){
    }

    private void init(){
        //扫描包，加载cmd
        initCommand();

        if(wsMsgHandler == null){
            wsMsgHandler = new MyWsMsgHandler();
        }
        if(port == null){
            port = getPort();
        }
        //设置监听器
        if(serverAioListener == null){
            serverAioListener = new MyWsServerAioListener();
        }
        try{
            wsServerStarter = new WsServerStarter(port,wsMsgHandler);
        }catch (Exception e){
            logger.error("builder MyWebsocketStarter error",e);
        }

        serverGroupContext = wsServerStarter.getServerGroupContext();
        serverGroupContext.setName("myWebSocket");
        serverGroupContext.setServerAioListener(serverAioListener);

        //设置ip监控
//        serverGroupContext.setIpStatListener();
        //设置ip监控时间段
//        serverGroupContext.ipStats.addDurations();

        //设置心跳超时时间
//        serverGroupContext.setHeartbeatTimeout();

        /*if (P.getInt("ws.use.ssl", 1) == 1) {
            //如果你希望通过wss来访问，就加上下面的代码吧，不过首先你得有SSL证书（证书必须和域名相匹配，否则可能访问不了ssl）
//			String keyStoreFile = "classpath:config/ssl/keystore.jks";
//			String trustStoreFile = "classpath:config/ssl/keystore.jks";
//			String keyStorePwd = "214323428310224";


            String keyStoreFile = P.get("ssl.keystore", null);
            String trustStoreFile = P.get("ssl.truststore", null);
            String keyStorePwd = P.get("ssl.pwd", null);
            serverGroupContext.useSsl(keyStoreFile, trustStoreFile, keyStorePwd);
        }*/
    }

    public void start() throws Exception {
        init();
        //加载属性文件
//        P.use("app.properties");

        //启动http server，这个步骤不是必须的，但是为了用页面演示websocket，所以先启动http
        /*if (P.getInt("start.http", 1) == 1) {
            HttpServerInit.init();
        }*/
        wsServerStarter.start();
    }


    public ServerGroupContext getServerGroupContext() {
        return serverGroupContext;
    }

    public WsServerStarter getWsServerStarter() {
        return wsServerStarter;
    }

    public void setPort(int port){
        this.port = port;
    }

    public void setWsMsgHandler(IWsMsgHandler wsMsgHandler){
        this.wsMsgHandler = wsMsgHandler;
    }

    private int getPort(){
        int defaultPort = 8888;
        return getPort(defaultPort);
    }

    private int getPort(int port){
        ServerSocket socket = null;
        try{
            socket = new ServerSocket(port);
        }catch (IOException e){
            ++port;
            return getPort(port);
        }finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    logger.error("");
                }
            }
        }
        return port;
    }

    public ServerAioListener getServerAioListener() {
        return serverAioListener;
    }

    public void setServerAioListener(ServerAioListener serverAioListener) {
        this.serverAioListener = serverAioListener;
    }

    public String getCmdScanPakage() {
        return cmdScanPakage;
    }

    public void setCmdScanPakage(String cmdScanPakage) {
        this.cmdScanPakage = cmdScanPakage;
    }

    private void initCommand(){
        try{
            Set<Class<?>> set = ClassUtil.scanPackage(cmdScanPakage);
            Object obj = null;
            List<CmdProcess> list = new ArrayList<>();
            if(CollectionUtil.isNotEmpty(set)){
                for (Class clazz : set) {
                    obj = clazz.newInstance();
                    if(obj instanceof CmdProcess){
                        list.add((CmdProcess) obj);
                    }
                }
            }

            CommandManager.getInstance().addCmdProcesss(list);
            logger.info("init IM cmd repository success ! count [" + list.size() + "]");
        }catch (Exception e){
            logger.error("init cmd repositry error",e);
        }
    }
}
