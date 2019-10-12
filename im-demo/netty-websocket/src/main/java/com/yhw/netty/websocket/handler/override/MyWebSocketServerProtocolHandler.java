package com.yhw.netty.websocket.handler.override;

import com.yhw.netty.websocket.listener.SimpleAuthListener;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.Utf8FrameValidator;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.util.AttributeKey;

import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author yhw
 */
public class MyWebSocketServerProtocolHandler extends WebSocketServerProtocolHandler {

    private  String websocketPath;
    private  String subprotocols;
    private  boolean allowExtensions;
    private  int maxFramePayloadLength = 65536;
    private  boolean allowMaskMismatch;
    private  boolean checkStartsWith;


    public MyWebSocketServerProtocolHandler(String websocketPath) {
        super(websocketPath);
        this.websocketPath = websocketPath;
        this.subprotocols = null;
        this.allowExtensions = false;
    }

    public MyWebSocketServerProtocolHandler(String websocketPath, String subprotocols, boolean allowExtensions) {
        super(websocketPath,subprotocols,allowExtensions);
        this.websocketPath = websocketPath;
        this.subprotocols = subprotocols;
        this.allowExtensions = allowExtensions;
    }

    private static final AttributeKey<WebSocketServerHandshaker> MY_HANDSHAKER_ATTR_KEY =
            AttributeKey.valueOf(WebSocketServerHandshaker.class, "HANDSHAKER");

    static void setHandshaker(Channel channel, WebSocketServerHandshaker handshaker) {
        channel.attr(MY_HANDSHAKER_ATTR_KEY).set(handshaker);
    }

    static ChannelHandler forbiddenHttpRequestResponder() {
        return new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                if (msg instanceof FullHttpRequest) {
                    ((FullHttpRequest) msg).release();
                    FullHttpResponse response =
                            new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.FORBIDDEN);
                    ctx.channel().writeAndFlush(response);
                } else {
                    ctx.fireChannelRead(msg);
                }
            }
        };
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
//        super.handlerAdded(ctx);
        ChannelPipeline cp = ctx.pipeline();
        System.err.println("============== 握手handler======================");
        if (cp.get(MyWebSocketServerProtocolHandshakeHandler.class) == null) {
            // Add the WebSocketHandshakeHandler before this one.
            ctx.pipeline().addBefore(ctx.name(), MyWebSocketServerProtocolHandshakeHandler.class.getName(),
                    new MyWebSocketServerProtocolHandshakeHandler(websocketPath, subprotocols,
                            allowExtensions, maxFramePayloadLength, allowMaskMismatch, checkStartsWith,new SimpleAuthListener()));
        }
        System.err.println("====================================");
        if (cp.get(Utf8FrameValidator.class) == null) {
            // Add the UFT8 checking before this one.
            ctx.pipeline().addBefore(ctx.name(), Utf8FrameValidator.class.getName(),
                    new Utf8FrameValidator());
        }
    }


    public static final class HandshakeComplete {
        private final String requestUri;
        private final HttpHeaders requestHeaders;
        private final String selectedSubprotocol;

        HandshakeComplete(String requestUri, HttpHeaders requestHeaders, String selectedSubprotocol) {
            this.requestUri = requestUri;
            this.requestHeaders = requestHeaders;
            this.selectedSubprotocol = selectedSubprotocol;
        }

        public String requestUri() {
            return requestUri;
        }

        public HttpHeaders requestHeaders() {
            return requestHeaders;
        }

        public String selectedSubprotocol() {
            return selectedSubprotocol;
        }
    }
}
