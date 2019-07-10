package com.yhw.cloud.ws.wsprocess;

import com.alibaba.fastjson.JSONObject;
import com.yhw.tio.websocket.cmd.AuthCmdProcess;
import com.yhw.tio.websocket.packets.Message;
import org.tio.core.ChannelContext;

/**
 * Created by YHW on 2019/6/22.
 */
public class AuthProcess extends AuthCmdProcess {

    @Override
    public Message handler(JSONObject paramter, ChannelContext channelContext) {
        System.out.println("我进来了！！！！！！！！！！！！！！");
//        AuthReq authReq = paramter.toJavaObject(AuthReq.class);


        //进行业务校验

        //验证成功，返回
        return new Message();
    }

    @Override
    public void onSuccess(ChannelContext channelContext) {
        System.out.println("认证成功回调参数！！！！");
    }

}
