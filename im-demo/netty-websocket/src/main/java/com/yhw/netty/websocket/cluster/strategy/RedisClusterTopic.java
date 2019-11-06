package com.yhw.netty.websocket.cluster.strategy;

import cn.hutool.json.JSONUtil;
import com.yhw.netty.websocket.cluster.ImClusterTopic;
import com.yhw.netty.websocket.config.ImConfig;
import com.yhw.netty.websocket.packets.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * redis实现发布订阅
 * @author yhw
 */
public class RedisClusterTopic implements ImClusterTopic {


    @Override
    public void publish(Message message) {
        ImConfig.getImConfig().getRedisComponent().send("im:netty", JSONUtil.toJsonStr(message));
    }

    @Override
    public void consumer() {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(ImConfig.getImConfig().getRedisComponent().getRedisTemplate().getConnectionFactory());

        redisMessageListenerContainer.addMessageListener(new MessageListener() {
            @Override
            public void onMessage(org.springframework.data.redis.connection.Message message, byte[] pattern) {
               String messageStr = new String(message.getBody());
               System.out.println(messageStr);
            }
        }, new PatternTopic("im:netty"));
    }
}
