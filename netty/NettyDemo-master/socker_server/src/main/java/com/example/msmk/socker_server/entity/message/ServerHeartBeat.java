package com.example.msmk.socker_server.entity.message;

import com.example.msmk.socker_server.entity.message.base.SuperResponse;

/**
 * Author：Alex
 * Date：2019/6/6
 * Note：
 */
public class ServerHeartBeat extends SuperResponse {

    private String from;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
