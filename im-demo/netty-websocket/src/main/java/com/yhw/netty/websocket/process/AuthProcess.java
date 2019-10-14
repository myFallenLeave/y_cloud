package com.yhw.netty.websocket.process;

import com.yhw.netty.websocket.context.ImChannelContext;
import com.yhw.netty.websocket.model.ImUser;

/**
 * web认证
 * @author yhw
 */
public abstract class AuthProcess {

    /**
     * 登录
     * @param userName 用户名
     * @param password 用户密码
     * @return IM用户对象
     */
    public abstract ImUser login(String userName,String password);

    /**
     * 用户登录成功回调操作
     * @param context 上下文
     */
    public abstract void loginSuccess(ImChannelContext context);


}
