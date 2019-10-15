package com.yhw.netty.websocket.packets;

import com.yhw.netty.websocket.constants.NtConstant;
import lombok.Data;

/**
 * 聊天消息
 * @author yhw
 */
@Data
public class ChatMessage<T> extends Message{

    /**
     * 消息发送者
     */
    private String formUserId;

    /**
     * 消息接收者
     */
    private String toUserId;

    /**
     * 消息实体
     */
    private T data;

    @Override
    public Integer getCmd() {
        return NtConstant.CHAT_CODE;
    }
}
