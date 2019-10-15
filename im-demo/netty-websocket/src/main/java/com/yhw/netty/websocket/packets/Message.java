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

    public abstract Integer getCmd();


}
