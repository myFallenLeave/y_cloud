package com.yhw.netty.websocket.context;

import io.netty.channel.Channel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yhw
 */
@NoArgsConstructor
@Data
public class ImChannelContent {

    private String userId;

    private Channel channel;

    public ImChannelContent(String userId, Channel channel) {
        this.userId = userId;
        this.channel = channel;
    }
}
