package com.yhw.netty.websocket.handler;

import com.yhw.netty.websocket.context.ImContextRepository;
import com.yhw.netty.websocket.event.LifeCycleEvent;
import com.yhw.netty.websocket.handler.override.MyWebSocketServerProtocolHandler;
import com.yhw.netty.websocket.process.AuthProcess;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.ssl.SslContext;

/**
 * 初始化
 * @author yhw
 */
public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {


    private SslContext sslCtx;
    String websocketPath;
    String subprotocols;
    boolean allowExtensions;
    boolean checkStartsWith;
    AuthProcess authProcess;
    LifeCycleEvent lifeCycleEvent;

    private ImContextRepository contextRepository;

    private WebSocketServerInitializer(){}

    public WebSocketServerInitializer(SslContext sslCtx,String websocketPath, AuthProcess authProcess, LifeCycleEvent lifeCycleEvent,ImContextRepository contextRepository){
        this(sslCtx,websocketPath,null,true,true,authProcess,lifeCycleEvent,contextRepository);
    }

    public WebSocketServerInitializer(SslContext sslCtx,String websocketPath, String subprotocols, boolean allowExtensions, boolean checkStartsWith, AuthProcess authProcess, LifeCycleEvent lifeCycleEvent,ImContextRepository contextRepository){
        this.sslCtx = sslCtx;
        this.websocketPath = websocketPath;
        this.subprotocols = subprotocols;
        //需要 设置为true(运行拓展)
        this.allowExtensions = true;
        this.checkStartsWith = checkStartsWith;
        this.authProcess = authProcess;
        this.lifeCycleEvent = lifeCycleEvent;
        this.contextRepository = contextRepository;

    }

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if (sslCtx != null) {
            pipeline.addLast(sslCtx.newHandler(ch.alloc()));
        }
        //http 编码解码器
        pipeline.addLast(new HttpServerCodec());
        //把多个消息转换为一个单一的FullHttpRequest或是FullHttpResponse，原因是HTTP解码器会在每个HTTP消息中生成多个消息对象
        pipeline.addLast(new HttpObjectAggregator(65536));
        //WebSocket数据压缩
        pipeline.addLast(new WebSocketServerCompressionHandler());
        //webSocket 协议处理
//        pipeline.addLast(new WebSocketServerProtocolHandler(websocketPath, null, true));
        pipeline.addLast(new MyWebSocketServerProtocolHandler(websocketPath,subprotocols,allowExtensions,checkStartsWith,authProcess, lifeCycleEvent));
//        pipeline.addLast(new MyWebSocketServerProtocolHandler(websocketPath,subprotocols,allowExtensions,checkStartsWith,authProcess, lifeCycleEvent));
        //http 首页处理器
        pipeline.addLast(new WebSocketIndexPageHandler(websocketPath));
        //webSocket处理器
        pipeline.addLast(new WebSocketFrameHandler(contextRepository));
    }
}
