package com.yhw.netty.websocket.packets;

import com.yhw.netty.websocket.constants.NtConstant;
import lombok.Data;

/**
 * 群组消息
 * @author yhw
 */
@Data
public class ChatGroupMessage<T> extends Message {

    /**
     * 消息发送者
     */
    private String formUserId;

    /**
     * 消息接收群组
     */
    private String toGroupId;

    /**
     * 消息实体
     */
    private T data;


    @Override
    public Integer getCmd() {
        return NtConstant.CHAT_GROUP_CODE;
    }
}
