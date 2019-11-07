package com.yhw.im.netty.example.config;

import com.yhw.netty.websocket.context.ImChannelContent;
import com.yhw.netty.websocket.event.LifeCycleEvent;
import com.yhw.netty.websocket.model.ImUser;
import io.netty.channel.Channel;

public class TestLifeCycleEvent extends LifeCycleEvent {

    @Override
    public void bindContext(ImUser login, ImChannelContent imChannelContext, Channel channel){
        super.bindContext(login,imChannelContext,channel);
    }

    /**
     * 解绑操作
     * @param channel
     */
    @Override
    public void cleanContext(Channel channel){
        super.cleanContext(channel);
    }
}
