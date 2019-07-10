package com.yhw.tio.websocket.cmd;

import com.yhw.tio.websocket.command.Command;
import org.tio.core.ChannelContext;

/**
 * Created by YHW on 2019/7/6.
 */
public abstract class AuthCmdProcess implements CmdProcess {


    /**
     * login success callBack
     */
    public abstract void onSuccess(ChannelContext channelContext);

    @Override
    public Command getCommand() {
        return Command.AUTH;
    }
}
