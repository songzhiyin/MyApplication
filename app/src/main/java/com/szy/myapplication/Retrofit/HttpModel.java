package com.szy.myapplication.Retrofit;


import java.util.Map;

import okhttp3.RequestBody;
import rx.Subscriber;

/**
 * Created by bingju on 2017/2/15.
 */

public class HttpModel extends HttpModelBase {
    private int id = 0;

    /**
     * 用户登录
     */
    public void login_server(Map<String, Object> map, Subscriber subscriber) {
        Map<String, Object> maps = getMap(map);
        setModel(retrofitApiSerivce.login(maps), subscriber);
    }
    /**
     * 上传用户头像
     */
    public void uploadAvatar(Map<String, RequestBody> map, Subscriber subscriber) {
        Map<String, RequestBody> maps = getMapRequestBody(map);
        setModel(retrofitApiSerivce.uploadAvatar(maps), subscriber);
    }
    /**
     * 获取我的消息列表
     */
    public void getMessageList(Map<String, Object> map, Subscriber subscriber) {
        Map<String, Object> maps = getMap(map);
        setModel(retrofitApiSerivce.getMessageList(maps), subscriber);
    }

}
