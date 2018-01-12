package com.szy.myapplication.Base;

/**
 * Created by bingju on 2017/2/14.
 */

public class BaseEntity {
    private int code;//响应码
    private String message;//接口请求说明

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
