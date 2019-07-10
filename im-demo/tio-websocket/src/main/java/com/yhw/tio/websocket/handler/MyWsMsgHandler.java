package com.yhw.tio.websocket.handler;

import com.alibaba.fastjson.JSONObject;
import com.yhw.tio.websocket.cmd.AuthCmdProcess;
import com.yhw.tio.websocket.cmd.CmdProcess;
import com.yhw.tio.websocket.command.Command;
import com.yhw.tio.websocket.command.CommandManager;
import com.yhw.tio.websocket.packets.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;
import org.tio.websocket.common.WsSessionContext;
import org.tio.websocket.server.handler.IWsMsgHandler;

import java.util.Objects;

public class MyWsMsgHandler implements IWsMsgHandler {

    private static Logger logger = LoggerFactory.getLogger(MyWsMsgHandler.class);

    /**
     * 第一次握手
     * 业务可以在这里获取cookie，request参数等
     * @param request
     * @param httpResponse
     * @param channelContext
     * @return
     * @throws Exception
     */
    @Override
    public HttpResponse handshake(HttpRequest request, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        String clientip = request.getClientIp();
        String userId = request.getParam("userId");
        String token = request.getParam("token");
        JSONObject parmterJson = new JSONObject();
        parmterJson.put("userId",userId);
        parmterJson.put(token,token);

        //进行身份校验,校验成功绑定用户,否则关闭通道
        AuthCmdProcess process = (AuthCmdProcess) CommandManager.getInstance().getCmdProcess(Command.AUTH);
        //如果认证返回为空，则认证失败，断开连接
        Message message = process.handler(parmterJson,channelContext);
        if(message == null){
            Tio.close(channelContext,"auth error");
        }

        //通道绑定用户
        Tio.bindUser(channelContext, userId);
        //认证成功回调
        process.onSuccess(channelContext);

//		channelContext.setUserid(myname);
        logger.info("收到来自{}的ws握手包\r\n{}", clientip, request.toString());
        return httpResponse;
    }

    /**
     * 握手成功
     * @param httpRequest
     * @param httpResponse
     * @param channelContext
     * @throws Exception
     */
    @Override
    public void onAfterHandshaked(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        //绑定到群组，后面会有群发
        /*Tio.bindGroup(channelContext, Const.GROUP_ID);
        int count = Tio.getAllChannelContexts(channelContext.groupContext).getObj().size();

        String msg = "{name:'admin',message:'" + channelContext.userid + " 进来了，共【" + count + "】人在线" + "'}";
        //用tio-websocket，服务器发送到客户端的Packet都是WsResponse
        WsResponse wsResponse = WsResponse.fromText(msg, ShowcaseServerConfig.CHARSET);
        //群发
        Tio.sendToGroup(channelContext.groupContext, Const.GROUP_ID, wsResponse);*/
    }

    /**
     * 字节消息（binaryType = arraybuffer）过来后会走这个方法
     * @param wsRequest
     * @param bytes
     * @param channelContext
     * @return
     * @throws Exception
     */
    @Override
    public Object onBytes(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        return null;
    }

    /**
     * 字符消息（binaryType = blob）过来后会走这个方法
     * @param wsRequest
     * @param text
     * @param channelContext
     * @return 返回JSONString
     * @throws Exception
     */
    @Override
    public Object onText(WsRequest wsRequest, String text, ChannelContext channelContext) throws Exception {
       /* WsSessionContext wsSessionContext = (WsSessionContext) channelContext.getAttribute();
        HttpRequest httpRequest = wsSessionContext.getHandshakeRequest();//获取websocket握手包
        if (logger.isDebugEnabled()) {
            logger.debug("握手包:{}", httpRequest);
        }

//		log.info("收到ws消息:{}", text);

        if (Objects.equals("心跳内容", text)) {
            return null;
        }
        //channelContext.getToken()
        //String msg = channelContext.getClientNode().toString() + " 说：" + text;
        String msg = "{name:'" + channelContext.userid + "',message:'" + text + "'}";
        //用tio-websocket，服务器发送到客户端的Packet都是WsResponse
        WsResponse wsResponse = WsResponse.fromText(msg, ShowcaseServerConfig.CHARSET);
        //群发
        Tio.sendToGroup(channelContext.groupContext, Const.GROUP_ID, wsResponse);*/

        JSONObject jsonObject = null;
        Command command = null;
        try{
            jsonObject = JSONObject.parseObject(text);
            Integer commandCode = jsonObject.getInteger("code");
            if(commandCode == null){
                //返回null,记录日志
            }
            command = Command.valueOf(commandCode);
        }catch (Exception e){

        }


        CmdProcess process = CommandManager.getInstance().getCmdProcess(command);
        if(process != null){
            Message message = process.handler(jsonObject,channelContext);
            if(message != null){
                return JSONObject.toJSONString(message);
            }
        }

        //返回值是要发送给客户端的内容，一般都是返回null
        return null;
    }

    /**
     * 关闭连接回调(不需要实现业务，相关解绑已在listener中实现)
     * @param wsRequest
     * @param bytes
     * @param channelContext
     * @return
     * @throws Exception
     */
    @Override
    public Object onClose(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        Tio.remove(channelContext, "receive close flag");
        return null;
    }
}
