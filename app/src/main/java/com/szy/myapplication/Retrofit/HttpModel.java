package com.szy.myapplication.Retrofit;




import java.util.Map;

import io.reactivex.Observer;
import okhttp3.RequestBody;

/**
 * Created by bingju on 2017/2/15.
 */

public class HttpModel extends HttpModelBase {
    private int id = 0;

    /**
     * 用户登录
     */
    public void login_server(Map<String, Object> map, Observer subscriber) {
        Map<String, Object> maps = getMap(map);
        setModel(retrofitApiSerivce.login(maps), subscriber);
    }

    /**
     * 上传用户头像
     */
    public void uploadAvatar(Map<String, RequestBody> map, Observer subscriber) {
        Map<String, RequestBody> maps = getMapRequestBody(map);
        setModel(retrofitApiSerivce.uploadAvatar(maps), subscriber);
    }

    /**
     * 获取我的消息列表
     */
    public void getMessageList(Map<String, Object> map, Observer subscriber) {
        Map<String, Object> maps = getMap(map);
        setModel(retrofitApiSerivce.getMessageList(maps), subscriber);
    }

    /**
     * 获取我的美女图片列表
     */
    public void getBellePhones(String page, Observer subscriber) {
        setModel(retrofitApiSerivce.getBellePhones(page), subscriber);
    }
}
