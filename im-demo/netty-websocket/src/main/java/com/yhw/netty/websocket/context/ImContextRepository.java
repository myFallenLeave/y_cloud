package com.yhw.netty.websocket.context;

import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yhw
 */
public final class ImContextRepository {

    int CONTEXT_MAX_SIZE = 3;

    private ImContextRepository(){}

    public static ImContextRepository getInstance(){
        return Holder.SINGLE;
    }

    private static Map<String, List<ImChannelContext>> repository = new ConcurrentHashMap<>();

    public Map<String, List<ImChannelContext>> getAllChannel() {
        return repository;
    }

    public List<ImChannelContext> getByUserId(String userId) {
        List<ImChannelContext> contexts = repository.get(userId);
        if(contexts == null || contexts.isEmpty()){
            contexts = new ArrayList<>();
        }
        return contexts;
    }

    public ImChannelContext getByUserIdAndChannel(String userId, Channel channel) {
        List<ImChannelContext> contexts = getByUserId(userId);
        for (ImChannelContext obj : contexts) {
            if(channel == obj.getChannel()){
                return obj;
            }
        }
        return null;
    }

    public boolean saveImChannelContext(ImChannelContext context) {
        List<ImChannelContext> contexts = getByUserId(context.getUserId());

        if(!contexts.isEmpty() && contexts.size() >= CONTEXT_MAX_SIZE){
            contexts.get(0).getChannel().close();
            contexts = contexts.subList(1,CONTEXT_MAX_SIZE);
        }
        contexts.add(context);
        repository.put(context.getUserId(),contexts);
        return true;
    }

    public boolean removeImChannelContext(ImChannelContext context) {
        List<ImChannelContext> contexts =getByUserId(context.getUserId());
        if(contexts.contains(context)){
            contexts.remove(context);
            return true;
        }
        ImChannelContext channelContext = null;
        Channel channel = context.getChannel();
        for (ImChannelContext obj : contexts) {
            if(channel == obj.getChannel()){
                channelContext =  obj;
                break;
            }
        }
        if(channelContext != null){
            contexts.remove(channelContext);
        }
        return true;
    }

    private static class Holder{
        private static ImContextRepository SINGLE = new ImContextRepository();
    }
}
