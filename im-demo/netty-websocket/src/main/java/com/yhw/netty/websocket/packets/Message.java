package com.yhw.netty.websocket.packets;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息包基类
 * @author yhw
 */
@NoArgsConstructor
@Data
public abstract class Message {
    /**
     * 指令代码
     */
    private Integer cmd;

    public abstract Integer getCmd();


}
