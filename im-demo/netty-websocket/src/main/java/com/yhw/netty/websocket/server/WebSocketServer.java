package com.yhw.netty.websocket.server;

import com.yhw.netty.websocket.context.ImContextRepository;
import com.yhw.netty.websocket.event.LifeCycleEvent;
import com.yhw.netty.websocket.handler.WebSocketServerInitializer;
import com.yhw.netty.websocket.process.AuthProcess;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

/**
 * @author yhw
 */
public class WebSocketServer {

    /**
     * 是否启用ssl
     */
    private boolean ssl = false;
    private int port;


    private ServerBootstrap serverBootstrap;
    private NioEventLoopGroup boss;
    private NioEventLoopGroup work;

    private WebSocketServer(){
    }

    public WebSocketServer(int port,String websocketPath,boolean ssl,AuthProcess authProcess){
        this.ssl = ssl;
        this.port = port;
        //this.websocketPath = websocketPath;
        //this.authProcess = authProcess;
        ImContextRepository contextRepository = ImContextRepository.getInstance();
        LifeCycleEvent lifeCycleEvent = new LifeCycleEvent(contextRepository);

        serverBootstrap = new ServerBootstrap();
        boss = new NioEventLoopGroup(1);
        work = new NioEventLoopGroup();

        serverBootstrap.group(boss,work)
                .channel(NioServerSocketChannel.class)
                .childHandler(new WebSocketServerInitializer(getSslContext(),websocketPath,authProcess,lifeCycleEvent,contextRepository));
    }

    private SslContext getSslContext(){
        SslContext sslCtx = null;
        try{
            if (ssl) {
                SelfSignedCertificate ssc = new SelfSignedCertificate();
                sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return sslCtx;
    }

    public void start(){
        try {
            Channel ch = serverBootstrap.bind(port).sync().channel();

            System.out.println("Open your web browser and navigate to " +
                    (ssl ? "https" : "http") + "://127.0.0.1:" + port + '/');

            ch.closeFuture().sync();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }
}
