package com.yhw.netty.websocket.config;

import com.yhw.netty.websocket.constants.NtConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * @author yhw
 */
@ConfigurationProperties(prefix = NtConstant.IM_NT_PREFIX)
@Component
@Data
public class ImPropertiesConfig {

    /**
     * webSocket前缀
     */
    private String websocketPath;

    /**
     * 扫描消息包 路径
     */
    private String packetScan;

    /**
     * websocket 端口
     */
    private Integer port;

    /**
     * 是否启用ssl
     */
    private boolean ssl;


}
