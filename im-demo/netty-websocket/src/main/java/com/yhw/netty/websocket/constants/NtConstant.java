package com.yhw.netty.websocket.constants;

import com.yhw.netty.websocket.model.ImUser;
import io.netty.util.AttributeKey;

/**
 * @author yhw
 */
public interface NtConstant {

    /**
     * 消息体包扫描路径
     */
    String IM_NT_PREFIX = "im.ws";

    String IM_NETTY_PACKAGE_PATH = "com.yhw.netty.websocket";

    String DEFAULT_WEBSOCKET_PATH = "/websocket";

    String HANDSHAKER = "HANDSHAKER";

    AttributeKey<ImUser> SESSION = AttributeKey.newInstance("session");

    //消息cmd 码

    /**
     * 异常消息
     */
    Integer ERROR_CODE = 404;

    /**
     * 单聊消息
     */
    Integer CHAT_CODE = 0;

    /**
     * 群聊消息
     */
    Integer CHAT_GROUP_CODE = 1;


    //缓存key前缀

    /**
     * 群组缓存前缀(使用set存储)
     * example: im:group:1, im:group:2
     */
    String GROUP_PREFIX = "im:group:";



}
