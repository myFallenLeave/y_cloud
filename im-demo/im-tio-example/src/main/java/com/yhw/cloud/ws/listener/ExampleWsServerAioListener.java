package com.yhw.cloud.ws.listener;

import com.yhw.tio.websocket.listener.MyWsServerAioListener;
import org.tio.core.ChannelContext;

/**
 * Created by YHW on 2019/7/6.
 */
public class ExampleWsServerAioListener extends MyWsServerAioListener {

    @Override
    public void onBeforeClose(ChannelContext channelContext, Throwable throwable, String remark, boolean isRemove) throws Exception {
        super.onBeforeClose(channelContext, throwable, remark, isRemove);
        //进行通道相关解绑操作
        //例如用户在线状态变更
        System.out.println("通道关闭连接");
    }
}
