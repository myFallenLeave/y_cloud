package com.yhw.netty.websocket.context;

import com.yhw.netty.websocket.cluster.ImClusterTopic;
import com.yhw.netty.websocket.event.LifeCycleEvent;

/**
 * 全局上下文(从上下文中加载，设置)
 * @author yhw
 */
public final class GlobalContent {

    private LifeCycleEvent lifeCycleEvent;

    private ImClusterTopic imClusterTopic;

    private GlobalContent(){}

    public static GlobalContent getInstance(){
        return Single.INSTANCE;
    }

    public void  setLifeCycleEvent(LifeCycleEvent lifeCycleEvent){
        this.lifeCycleEvent = lifeCycleEvent;
    }

    public LifeCycleEvent getLifeCycleEvent(){
        /*if(lifeCycleEvent == null){

        }*/
        return lifeCycleEvent;
    }

    public ImClusterTopic getImClusterTopic(){
        /*if(imClusterTopic == null){

        }*/
        return imClusterTopic;
    }

    public void  setImClusterTopic(ImClusterTopic imClusterTopic){
        this.imClusterTopic = imClusterTopic;
    }


    private static class Single{
        private static GlobalContent INSTANCE = new GlobalContent();
    }

}
