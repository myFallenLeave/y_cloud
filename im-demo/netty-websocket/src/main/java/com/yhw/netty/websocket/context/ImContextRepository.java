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

    private static Map<String, List<ImChannelContent>> repository = new ConcurrentHashMap<>();

    public Map<String, List<ImChannelContent>> getAllChannel() {
        return repository;
    }

    public List<ImChannelContent> getByUserId(String userId) {
        List<ImChannelContent> contexts = repository.get(userId);
        if(contexts == null || contexts.isEmpty()){
            contexts = new ArrayList<>();
        }
        return contexts;
    }

    public ImChannelContent getByUserIdAndChannel(String userId, Channel channel) {
        List<ImChannelContent> contexts = getByUserId(userId);
        for (ImChannelContent obj : contexts) {
            if(channel == obj.getChannel()){
                return obj;
            }
        }
        return null;
    }

    public boolean saveImChannelContext(ImChannelContent context) {
        List<ImChannelContent> contexts = getByUserId(context.getUserId());

        if(!contexts.isEmpty() && contexts.size() >= CONTEXT_MAX_SIZE){
            contexts.get(0).getChannel().close();
            contexts = contexts.subList(1,CONTEXT_MAX_SIZE);
        }
        contexts.add(context);
        repository.put(context.getUserId(),contexts);
        return true;
    }

    public boolean removeImChannelContext(ImChannelContent context) {
        List<ImChannelContent> contexts =getByUserId(context.getUserId());
        if(contexts.contains(context)){
            contexts.remove(context);
            return true;
        }
        ImChannelContent channelContext = null;
        Channel channel = context.getChannel();
        for (ImChannelContent obj : contexts) {
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
