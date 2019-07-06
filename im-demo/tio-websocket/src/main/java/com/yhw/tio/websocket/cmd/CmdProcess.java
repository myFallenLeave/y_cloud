package com.yhw.tio.websocket.cmd;

import com.alibaba.fastjson.JSONObject;
import com.yhw.tio.websocket.command.Command;
import com.yhw.tio.websocket.packets.Message;
import org.tio.core.ChannelContext;

/**
 * Created by YHW on 2019/6/21.
 */
public interface CmdProcess {

    /**
     *
     * @param paramter
     * @param channelContext
     * @return
     */
    Message handler(JSONObject paramter,ChannelContext channelContext);


    Command getCommand();
}
