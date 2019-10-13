package com.yhw.netty.websocket.server;

import com.yhw.netty.websocket.constants.NtConstant;
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

    private int port;
    private String websocketPath;
    //是否启用ssl
    private boolean ssl = false;

    AuthProcess authListener;

    private ServerBootstrap serverBootstrap;
    private NioEventLoopGroup boss;
    private NioEventLoopGroup work;

    public WebSocketServer(){
        websocketPath = NtConstant.DEFAULT_WEBSOCKET_PATH;
        port = ssl ? 8843 : 8080;
    }


    public WebSocketServer(int port,String websocketPath,boolean ssl){
        this.ssl = ssl;
        this.port = port;
        this.websocketPath = websocketPath;

        serverBootstrap = new ServerBootstrap();
        boss = new NioEventLoopGroup(1);
        work = new NioEventLoopGroup();

        serverBootstrap.group(boss,work)
                .channel(NioServerSocketChannel.class)
                //.handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new WebSocketServerInitializer(getSslContext()));
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









    /*public static void main(String[] args) throws Exception {
        // Configure SSL.
        final SslContext sslCtx;
        if (SSL) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        } else {
            sslCtx = null;
        }

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    // .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new WebSocketServerInitializer(sslCtx));

            Channel ch = b.bind(PORT).sync().channel();

            System.out.println("Open your web browser and navigate to " +
                    (SSL? "https" : "http") + "://127.0.0.1:" + PORT + '/');

            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }*/
}
