package com.yhw.netty.websocket.packets;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 消息包基类
 * @author yhw
 */
@NoArgsConstructor
@Data
public abstract class Message {

    /**
     * 消息创建时间
     */
    private LocalDateTime createTime = LocalDateTime.now();

    /**
     * 指令代码
     */
    private Integer cmd;

    /**
     * 消息指令类型
     * 一般正常会存在3种情况
     * 无状态消息(即做 消息通知)
     * 单聊消息
     * 群聊消息
     * @return
     */
    public abstract Integer getCmd();


}
