package com.yhw.netty.websocket.cluster;


import com.yhw.netty.websocket.packets.Message;

/**
 * @author yhw
 */
public interface ImClusterTopic {

    /**
     * 发布消息
     */
    void publish(Message message);

    /**
     * 订阅消息(采用广播模式)
     */
    void consumer();
}
