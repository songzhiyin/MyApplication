package com.szy.myapplication.Retrofit;

import com.szy.myapplication.Base.BaseEntity;
import com.szy.myapplication.Entity.LoginEntity;
import com.szy.myapplication.Entity.NetMessageEntity;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2018/1/12 0012.
 */

public interface RetrofitApiSerivce {
    /**
     * 用户登录
     */
    @FormUrlEncoded
    @POST("login")
    Observable<LoginEntity> login(@FieldMap Map<String, Object> fields);

    /**
     * 上传头像
     */
    @Multipart
    @POST("/user/uploadAvatar")
    Observable<BaseEntity> uploadAvatar(@PartMap Map<String, RequestBody> fields);

    /**
     * 获取我的消息列表
     */
    @GET("/message/page")
    Observable<NetMessageEntity> getMessageList(@QueryMap Map<String, Object> fields);

    /**
     * 获取我的美女图片列表
     */
    @GET("data/福利/10/{page}")
    Observable<String> getBellePhones(@Path("page") String page);
}
