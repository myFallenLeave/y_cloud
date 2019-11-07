package com.yhw.netty.websocket.packets;

import com.yhw.netty.websocket.constants.NtConstant;

/**
 * 消息成功响应包
 * @author yhw
 */
public class OkResponseMessage extends Message {

    @Override
    public Integer getCmd() {
        return NtConstant.SUCCESS_CODE;
    }

    private OkResponseMessage(){}


    public static OkResponseMessage getInstance(){
        return Single.OK_MSG;
    }

    private static class Single{
        public static OkResponseMessage OK_MSG = new OkResponseMessage();
    }
}
