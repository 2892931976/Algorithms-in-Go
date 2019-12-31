package com.example.storage.bean.user;

/**
 * Created by Administrator on 2019/12/12.
 */

public class DataInfo {

    public UserInfo getData() {
        return data;
    }

    public void setData(UserInfo data) {
        this.data = data;
    }

    private UserInfo data;

    public DataInfo(UserInfo data) {
        super();
        this.data = data;
    }

    public DataInfo() {
        super();
    }
}
