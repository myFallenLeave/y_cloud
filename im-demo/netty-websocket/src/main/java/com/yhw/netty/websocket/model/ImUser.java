package com.yhw.netty.websocket.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Im用户
 * @author yhw
 */
@NoArgsConstructor
@Data
public class ImUser {

    /**
     * 用户唯一标识
     */
    private String userId;


    /**
     * 群组列表
     */
    private List<String> groupIds;

    public ImUser(String userId, List<String> groupIds){
        this.userId = userId;
        this.groupIds = groupIds;
    }

}
