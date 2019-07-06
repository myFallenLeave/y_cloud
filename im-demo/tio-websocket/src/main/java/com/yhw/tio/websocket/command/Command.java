package com.yhw.tio.websocket.command;

import java.util.HashMap;
import java.util.Map;

public enum Command {

    AUTH(0,"认证"),
    SEND_CHAT_REQ(1,"聊天请求"),
    SEND_CHAT_RESP(2,"聊天响应"),
    OPEN_SESSION(3,"打开会话"),
    CLOSE_SESSION(4,"移除会话"),
    GET_USER_INFO(5,"获取用户信息"),
    GET_SESSION_LIST(6,"获取会话列表"),
    ;

    private int code;
    private String description;

    private static final Map<Integer,Command> repositry = new HashMap<>();

    static {
        for(Command command : Command.values()){
            repositry.put(command.code,command);
        }
    }

    Command(){}

    Command(int code,String description){
        this.code = code;
        this.description = description;
    }

    public static Command valueOf(int code){
        return repositry.get(code);
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
