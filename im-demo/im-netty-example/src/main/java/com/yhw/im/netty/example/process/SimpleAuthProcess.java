package com.yhw.im.netty.example.process;

import com.yhw.netty.websocket.context.ImChannelContent;
import com.yhw.netty.websocket.model.ImUser;
import com.yhw.netty.websocket.process.AuthProcess;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 认证执行器
 * @author yhw
 */
@Component
public class SimpleAuthProcess extends AuthProcess {

    private static Map<String, ImUser> userRepository;

    static {
        userRepository = new HashMap<>();
        List<String> groupIds = new ArrayList<>();
        groupIds.add("11111111");
        groupIds.add("22222222");
        userRepository.put("zhangsan",new ImUser("123456",groupIds));
        userRepository.put("lisi",new ImUser("654321",groupIds));
    }

    @Override
    public ImUser login(String userName, String password) {
        return userRepository.get(userName);
    }

    @Override
    public void loginSuccess(ImChannelContent context) {
        String logintSuccessMsg = context.getUserId().concat("登录成功！");
        context.getChannel().writeAndFlush(new TextWebSocketFrame(logintSuccessMsg));

        //一般登录成功需要做离线消息推送
        //获取单聊消息，推送到登录用户
        //获取群聊消息，推送到登录用户(注意使用分页查询，不要推送全部消息)
    }
}
