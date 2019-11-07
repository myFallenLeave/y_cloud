package com.yhw.netty.websocket.packets;

import com.yhw.netty.websocket.constants.NtConstant;

/**
 * 无状态消息
 * 即后台服务推送给在线连接 的消息(消息通知,如设备上线下线)
 * @author yhw
 */
public class NoStatusMessage<T> extends Message {

    /**
     * 消息实体
     */
    private T data;

    @Override
    public Integer getCmd() {
        return NtConstant.NO_STATUE_CODE;
    }


}
