package com.yhw.im.netty.example.packet;

import com.yhw.netty.websocket.packets.Message;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yhw
 */
@NoArgsConstructor
@Data
public class TestMessage extends Message {

    private String data;

    public TestMessage(String data,Integer cmd){
        this.data = data;
    }

    @Override
    public Integer getCmd() {
        return 1;
    }
}
