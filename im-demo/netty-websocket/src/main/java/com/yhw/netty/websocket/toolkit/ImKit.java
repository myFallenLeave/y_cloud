package com.yhw.netty.websocket.toolkit;

import cn.hutool.json.JSONUtil;
import com.yhw.netty.websocket.config.ImConfig;
import com.yhw.netty.websocket.context.ImChannelContent;
import com.yhw.netty.websocket.context.ImContextRepository;
import com.yhw.netty.websocket.packets.Message;
import com.yhw.netty.websocket.packets.NoStatusMessage;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author yhw
 */
@Slf4j
public class ImKit {

    /**
     * 发送无状态消息
     * @param message 消息体
     */
    public static void sendNoStatusMsg(NoStatusMessage message){
        sendAll(message);
    }

    /**
     * 发送消息给单个用户
     * @param userId 用户id
     * @param message 消息体
     */
    public static void sendUser(String userId, Message message){
        List<ImChannelContent> contexts = ImContextRepository.getInstance().getByUserId(userId);
        if(contexts == null || contexts.isEmpty()){
            //分布式环境下情况
        }else {
            sendUsers(contexts,message);
        }
    }

    /**
     * 发送消息
     * @param message
     */
    public static void sendAll(Message message){
        //如果是多节点部署，则发送消息到mq
        if(ImConfig.getImConfig().getPropertiesConfig().isCluster()){
            ImConfig.getImConfig().getImClusterTopic().publish(message);
        }else {
            sendNodeAll(message);
        }
    }

    /**
     * 发送消息 -> 当前节点所有用户
     * @param message 消息体
     */
    public static void sendNodeAll(Message message){
        Map<String, List<ImChannelContent>> allChannel = ImContextRepository.getInstance().getAllChannel();
        for (String userId :allChannel.keySet()) {
            sendUsers(allChannel.get(userId),message);
        }
    }

    public static void sendGroup(String groupId,Message message){
        //多节点情况下，直接推送到消息队列
    }

    private static void sendUsers(List<ImChannelContent> contexts, Message message){
        TextWebSocketFrame textFrame;
        for (ImChannelContent obj : contexts) {
            textFrame = getMessage(message);
            if(textFrame != null){
                obj.getChannel().writeAndFlush(textFrame);
            }
        }
    }

    /**
     * 获取消息体
     * @param message 消息体
     * @return webSocket 消息体
     */
    public static TextWebSocketFrame getMessage(Message message){
        if(message == null){
            log.error(">>>>>>>>>>>> message can not be empty ");
            return null;
        }
        String result = JSONUtil.toJsonStr(message);
        return new TextWebSocketFrame(result.toUpperCase(Locale.US));
    }
}
