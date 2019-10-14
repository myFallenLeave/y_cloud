package com.yhw.netty.websocket.context;

import com.yhw.netty.websocket.packets.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yhw
 */
public class MessageManager {

    private Map<Integer,Message> msgRepository = new HashMap<>();

    public static MessageManager getInstance(){
        return Holder.manager;
    }

    public Message getMessage(int code){
        return msgRepository.get(code);
    }


    /**
     * 批量添加
     * @param messages
     */
    public void addMessages(List<Message> messages){
        if(messages != null && !messages.isEmpty()){
            for (Message message : messages) {
                msgRepository.put(message.getCmd(),message);
            }
        }
    }

    public void addMessage(Message message){
        msgRepository.put(message.getCmd(),message);
    }

    static class Holder{
        private static MessageManager manager = new MessageManager();
    }
}
