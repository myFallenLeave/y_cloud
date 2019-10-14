package com.yhw.netty.websocket.packets;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息异常包
 * @author yhw
 */
@NoArgsConstructor
@Data
public class ErrorMessage extends Message {

    private String msg;

    public ErrorMessage(String msg){
        this.msg = msg;
    }

    @Override
    public Integer getCmd() {
        return 404;
    }
}
