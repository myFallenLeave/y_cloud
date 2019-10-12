package com.yhw.netty.websocket.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 群组
 * @author yhw
 */
@NoArgsConstructor
@Data
public class ImGroup {

    /**
     * 群组唯一标识
     */
    private String groupId;

    private List<String> userIds;

    public ImGroup(String groupId, List<String> userIds){
        this.groupId = groupId;
        this.userIds = userIds;
    }
}
