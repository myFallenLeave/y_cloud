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

    Integer ERROR_CODE = 404;

    Integer CHAT_CODE = 0;

    Integer CHAT_GROUP_CODE = 1;
}
