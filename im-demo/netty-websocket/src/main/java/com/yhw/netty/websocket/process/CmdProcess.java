package com.yhw.netty.websocket.process;

import com.yhw.netty.websocket.context.ImChannelContext;
import com.yhw.netty.websocket.packets.Message;

/**
 * @author yhw
 */
public interface CmdProcess {

    /**
     * 消息接收处理
     * @param message 消息体
     * @param channelContext 上下文
     * @return 响应消息体(无响应,则返回null)
     */
    Message  handler(Message message, ImChannelContext channelContext);

    /**
     * 设置命令码
     * @return 命令码
     */
    Integer getCmdCode();
}
