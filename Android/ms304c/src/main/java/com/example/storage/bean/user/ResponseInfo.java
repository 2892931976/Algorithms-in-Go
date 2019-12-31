package com.example.storage.bean.user;


/**
 * Created by Administrator on 2019/12/6.
 */

//type HttpResponse struct {
//        Api  string      `json:"api"`
//        Code int         `json:"code"`
//        Err  string      `json:"err"`
//        Data interface{} `json:"data"`
//        }

public class ResponseInfo {

    private String api;
    private int code;
    private String err;
    private UserInfo data;

    public UserInfo getData() {
        return data;
    }

    public void setData(UserInfo data) {
        this.data = data;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }


    public ResponseInfo(int errorCode, String errorMsg, String api, int code, String err, UserInfo data) {
        super();
        this.api = api;
        this.code = code;
        this.err = err;
        this.data = data;
    }

    public ResponseInfo() {
        super();
    }
}