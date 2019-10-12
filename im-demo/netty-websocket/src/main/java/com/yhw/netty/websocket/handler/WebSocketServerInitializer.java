package com.yhw.netty.websocket.handler;

import com.yhw.netty.websocket.handler.override.MyWebSocketServerProtocolHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.ssl.SslContext;

/**
 * 初始化
 * @author yhw
 */
public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {

    private static final String WEBSOCKET_PATH = "/websocket";

    private final SslContext sslCtx;

    public WebSocketServerInitializer(SslContext sslCtx) {
        this.sslCtx = sslCtx;
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
//        pipeline.addLast(new WebSocketServerProtocolHandler(WEBSOCKET_PATH, null, true));
        pipeline.addLast(new MyWebSocketServerProtocolHandler(WEBSOCKET_PATH, null, true));
        //http 处理器
        pipeline.addLast(new WebSocketIndexPageHandler(WEBSOCKET_PATH));
        //webSocket处理器
        pipeline.addLast(new WebSocketFrameHandler());
    }
}
