package com.yhw.netty.websocket.handler.override;

import com.yhw.netty.websocket.context.ImChannelContext;
import com.yhw.netty.websocket.listener.AuthListener;
import com.yhw.netty.websocket.model.ImUser;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.handler.ssl.SslHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpResponseStatus.FORBIDDEN;
import static io.netty.handler.codec.http.HttpUtil.isKeepAlive;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Handles the HTTP handshake (the HTTP Upgrade request) for {@link MyWebSocketServerProtocolHandler}.
 * @author yhw
 */
public class MyWebSocketServerProtocolHandshakeHandler extends ChannelInboundHandlerAdapter {

    private final String websocketPath;
    private final String subprotocols;
    private final boolean allowExtensions;
    private final int maxFramePayloadSize;
    private final boolean allowMaskMismatch;
    private final boolean checkStartsWith;

    private AuthListener authListener;

    MyWebSocketServerProtocolHandshakeHandler(String websocketPath, String subprotocols,
                                            boolean allowExtensions, int maxFrameSize, boolean allowMaskMismatch) {
        this(websocketPath, subprotocols, allowExtensions, maxFrameSize, allowMaskMismatch, false);
    }

    MyWebSocketServerProtocolHandshakeHandler(String websocketPath, String subprotocols,
                                            boolean allowExtensions, int maxFrameSize, boolean allowMaskMismatch, boolean checkStartsWith) {
        this.websocketPath = websocketPath;
        this.subprotocols = subprotocols;
        this.allowExtensions = allowExtensions;
        maxFramePayloadSize = maxFrameSize;
        this.allowMaskMismatch = allowMaskMismatch;
        this.checkStartsWith = checkStartsWith;
    }

    MyWebSocketServerProtocolHandshakeHandler(String websocketPath, String subprotocols,
                                              boolean allowExtensions, int maxFrameSize, boolean allowMaskMismatch, boolean checkStartsWith,AuthListener authListener) {
        this.websocketPath = websocketPath;
        this.subprotocols = subprotocols;
        this.allowExtensions = allowExtensions;
        maxFramePayloadSize = maxFrameSize;
        this.allowMaskMismatch = allowMaskMismatch;
        this.checkStartsWith = checkStartsWith;
        System.err.println("我被初始化了---------------------------------------");
        if(authListener == null){
            throw new IllegalStateException("authListener can not be empty ");
        }
        this.authListener = authListener;
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
        final FullHttpRequest req = (FullHttpRequest) msg;
        if (isNotWebSocketPath(req)) {
            ctx.fireChannelRead(msg);
            return;
        }

        try {
            if (!GET.equals(req.method())) {
                sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, FORBIDDEN));
                return;
            }

            final WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                    getWebSocketLocation(ctx.pipeline(), req, websocketPath), subprotocols,
                    allowExtensions, maxFramePayloadSize, allowMaskMismatch);
            final WebSocketServerHandshaker handshaker = wsFactory.newHandshaker(req);
            if (handshaker == null) {
                WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
            } else {
                final ChannelFuture handshakeFuture = handshaker.handshake(ctx.channel(), req);
                handshakeFuture.addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        if (!future.isSuccess()) {
                            ctx.fireExceptionCaught(future.cause());
                        } else {
                            // Kept for compatibility
                            ctx.fireUserEventTriggered(
                                    MyWebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE);
                            ctx.fireUserEventTriggered(
                                    new MyWebSocketServerProtocolHandler.HandshakeComplete(
                                            req.uri(), req.headers(), handshaker.selectedSubprotocol()));

                            //处理业务逻辑
                            processHandshake(req,ctx.channel());

                        }
                    }
                });
                MyWebSocketServerProtocolHandler.setHandshaker(ctx.channel(), handshaker);
                ctx.pipeline().replace(this, "WS403Responder",
                        MyWebSocketServerProtocolHandler.forbiddenHttpRequestResponder());
            }
        } finally {
            req.release();
        }
    }


    private boolean isNotWebSocketPath(FullHttpRequest req) {
        return checkStartsWith ? !req.uri().startsWith(websocketPath) : !req.uri().equals(websocketPath);
    }

    private static void sendHttpResponse(ChannelHandlerContext ctx, HttpRequest req, HttpResponse res) {
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!isKeepAlive(req) || res.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    private static String getWebSocketLocation(ChannelPipeline cp, HttpRequest req, String path) {
        String protocol = "ws";
        if (cp.get(SslHandler.class) != null) {
            // SSL in use so use Secure WebSockets
            protocol = "wss";
        }
        String host = req.headers().get(HttpHeaderNames.HOST);
        return protocol + "://" + host + path;
    }


    private void processHandshake(HttpRequest req, Channel channel){
        HttpMethod method = req.method();
        Map<String,String> parmMap = new HashMap<>();
        if (HttpMethod.GET == method) {
            // 是GET请求
            QueryStringDecoder decoder = new QueryStringDecoder(req.uri());
            decoder.parameters().entrySet().forEach( entry -> {
                // entry.getValue()是一个List, 只取第一个元素
                parmMap.put(entry.getKey(), entry.getValue().get(0));
            });
        }


        ImUser login = authListener.login(parmMap.get("userName"), parmMap.get("passwod"));

        if(login == null){
            System.err.println("认证失败");
//            channel.close();
        }
        //登录成功操作
        authListener.loginSuccess(new ImChannelContext(login.getUserId(),channel));
    }
}
