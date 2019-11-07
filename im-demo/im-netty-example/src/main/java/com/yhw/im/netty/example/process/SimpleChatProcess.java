package com.yhw.im.netty.example.process;

import com.yhw.netty.websocket.constants.NtConstant;
import com.yhw.netty.websocket.context.ImChannelContent;
import com.yhw.netty.websocket.packets.ChatMessage;
import com.yhw.netty.websocket.packets.Message;
import com.yhw.netty.websocket.packets.OkResponseMessage;
import com.yhw.netty.websocket.process.CmdProcess;
import com.yhw.netty.websocket.toolkit.ImKit;
import org.springframework.stereotype.Component;

/**
 * 单聊消息执行器
 * @author yhw
 */
@Component
public class SimpleChatProcess implements CmdProcess {

    @Override
    public Message handler(Message message, ImChannelContent channelContext) {
        //发送单聊消息
        ChatMessage chatMessage = (ChatMessage)message;

        //消息持久化(根据自己的业务进行存储)
        //...


        //发送消息
        ImKit.sendUser(chatMessage.getToUserId(),chatMessage);

        return OkResponseMessage.getInstance();
    }

    @Override
    public Integer getCmdCode() {
        return NtConstant.CHAT_CODE;
    }

}
