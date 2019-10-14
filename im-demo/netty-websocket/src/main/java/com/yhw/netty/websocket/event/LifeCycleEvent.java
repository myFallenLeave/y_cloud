package com.yhw.netty.websocket.event;

import com.yhw.netty.websocket.constants.NtConstant;
import com.yhw.netty.websocket.context.ImChannelContext;
import com.yhw.netty.websocket.context.ImContextRepository;
import com.yhw.netty.websocket.model.ImUser;
import io.netty.channel.Channel;

/**
 * channel 生命周期
 * @author yhw
 */
public class LifeCycleEvent {

    private ImContextRepository contextRepository;

    private LifeCycleEvent(){}

    public LifeCycleEvent(ImContextRepository contextRepository){
        this.contextRepository = contextRepository;
    }

    /**
     * 绑定上下文信息
     * @param login
     * @param imChannelContext
     * @param channel
     */
    public void bindContext(ImUser login,ImChannelContext imChannelContext,Channel channel){
        contextRepository.saveImChannelContext(imChannelContext);
        //通道绑定登录信息
        channel.attr(NtConstant.SESSION).set(login);
    }

    /**
     * 解绑操作
     * @param channel
     */
    public void cleanContext(Channel channel){
        ImUser imUser = channel.attr(NtConstant.SESSION).get();
        contextRepository.removeImChannelContext(new ImChannelContext(imUser.getUserId(),channel));
        channel.attr(NtConstant.SESSION).set(null);
    }
}
