package com.yhw.netty.websocket.packets;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yhw
 */
@NoArgsConstructor
@Data
public class Message {
    /**
     * 指令代码
     */
    private Integer cmd;

    public Message(Integer cmd){
        this.cmd = cmd;
    }

}
