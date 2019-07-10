package com.yhw.tio.websocket.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.core.intf.Packet;
import org.tio.websocket.common.WsSessionContext;
import org.tio.websocket.server.WsServerAioListener;

/**
 * ws监听
 */
public class MyWsServerAioListener extends WsServerAioListener {

    private static Logger logger = LoggerFactory.getLogger(MyWsServerAioListener.class);

    /**
     * 连接建立成功回调，使用父类，不需要其他实现
     * @param channelContext
     * @param isConnected
     * @param isReconnect
     * @throws Exception
     */
    @Override
    public void onAfterConnected(ChannelContext channelContext, boolean isConnected, boolean isReconnect) throws Exception {
        super.onAfterConnected(channelContext, isConnected, isReconnect);
        if (logger.isInfoEnabled()) {
            logger.info("onAfterConnected\r\n{}", channelContext);
        }
    }

    /**
     * 消息包发送之后触发本方法
     * @param channelContext
     * @param packet
     * @param isSentSuccess
     * @throws Exception
     */
    @Override
    public void onAfterSent(ChannelContext channelContext, Packet packet, boolean isSentSuccess) throws Exception {
        super.onAfterSent(channelContext, packet, isSentSuccess);
        /*if (logger.isInfoEnabled()) {
            logger.info("onAfterSent\r\n{}\r\n{}", packet.logstr(), channelContext);
        }*/
    }

    /**
     * 连接关闭前触发本方法
     * @param channelContext
     * @param throwable
     * @param remark
     * @param isRemove
     * @throws Exception
     */
    @Override
    public void onBeforeClose(ChannelContext channelContext, Throwable throwable, String remark, boolean isRemove) throws Exception {
        super.onBeforeClose(channelContext, throwable, remark, isRemove);
        if (logger.isInfoEnabled()) {
            logger.info("onBeforeClose\r\n{}", channelContext);
        }

        WsSessionContext wsSessionContext = (WsSessionContext) channelContext.getAttribute();

        if (wsSessionContext != null && wsSessionContext.isHandshaked()) {

            int count = Tio.getAllChannelContexts(channelContext.groupContext).getObj().size();

            String msg = channelContext.getClientNode().toString() + " 离开了，现在共有【" + count + "】人在线";
            logger.info(msg);
            //用tio-websocket，服务器发送到客户端的Packet都是WsResponse
//            WsResponse wsResponse = WsResponse.fromText(msg, "utf-8");
            //群发
//            Tio.sendToGroup(channelContext.groupContext, Const.GROUP_ID, wsResponse);
        }
    }

    /**
     * 解码成功后触发本方法
     * @param channelContext
     * @param packet
     * @param packetSize
     * @throws Exception
     */
    @Override
    public void onAfterDecoded(ChannelContext channelContext, Packet packet, int packetSize) throws Exception {
        super.onAfterDecoded(channelContext, packet, packetSize);
        if (logger.isInfoEnabled()) {
            logger.info("onAfterDecoded\r\n{}\r\n{}", packet.logstr(), channelContext);
        }
    }

    /**
     * 接收到TCP层传过来的数据后
     * @param channelContext
     * @param receivedBytes
     * @throws Exception
     */
    @Override
    public void onAfterReceivedBytes(ChannelContext channelContext, int receivedBytes) throws Exception {
        super.onAfterReceivedBytes(channelContext, receivedBytes);
        if (logger.isInfoEnabled()) {
            logger.info("onAfterReceivedBytes\r\n{}", channelContext);
        }
    }

    /**
     * 处理一个消息包后
     * @param channelContext
     * @param packet
     * @param cost
     * @throws Exception
     */
    @Override
    public void onAfterHandled(ChannelContext channelContext, Packet packet, long cost) throws Exception {
        super.onAfterHandled(channelContext, packet, cost);
        /*if (logger.isInfoEnabled()) {
            logger.info("onAfterHandled\r\n{}\r\n{}", packet.logstr(), channelContext);
        }*/
    }
}
