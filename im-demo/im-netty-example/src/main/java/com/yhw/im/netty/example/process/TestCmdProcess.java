package com.yhw.im.netty.example.process;

import com.yhw.netty.websocket.context.ImChannelContext;
import com.yhw.netty.websocket.packets.Message;
import com.yhw.netty.websocket.process.CmdProcess;
import org.springframework.stereotype.Component;

@Component
public class TestCmdProcess implements CmdProcess {

    @Override
    public Message handler(Message message, ImChannelContext channelContext) {
        return null;
    }

    @Override
    public Integer getCmdCode() {
        return 1;
    }

}
