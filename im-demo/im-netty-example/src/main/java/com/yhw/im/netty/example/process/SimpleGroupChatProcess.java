package com.yhw.im.netty.example.process;

import com.yhw.netty.websocket.constants.NtConstant;
import com.yhw.netty.websocket.context.ImChannelContent;
import com.yhw.netty.websocket.packets.ChatGroupMessage;
import com.yhw.netty.websocket.packets.Message;
import com.yhw.netty.websocket.packets.OkResponseMessage;
import com.yhw.netty.websocket.process.CmdProcess;
import com.yhw.netty.websocket.toolkit.ImKit;
import org.springframework.stereotype.Component;

/**
 * 群聊消息处理器
 * @author yhw
 */
@Component
public class SimpleGroupChatProcess implements CmdProcess {

    @Override
    public Message handler(Message message, ImChannelContent channelContext) {
        ChatGroupMessage chatGroupMessage = (ChatGroupMessage)message;

        //持久化群消息
        //...


        //发送消息
        ImKit.sendGroup(chatGroupMessage.getToGroupId(),chatGroupMessage);


        return OkResponseMessage.getInstance();
    }


    @Override
    public Integer getCmdCode() {
        return NtConstant.CHAT_GROUP_CODE;
    }
}
