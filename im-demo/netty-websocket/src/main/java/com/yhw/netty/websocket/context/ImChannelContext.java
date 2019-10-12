package com.yhw.netty.websocket.context;

import io.netty.channel.Channel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yhw
 */
@NoArgsConstructor
@Data
public class ImChannelContext {

    private String userId;

    private Channel channel;

    public ImChannelContext(String userId,Channel channel) {
        this.userId = userId;
        this.channel = channel;
    }
}
