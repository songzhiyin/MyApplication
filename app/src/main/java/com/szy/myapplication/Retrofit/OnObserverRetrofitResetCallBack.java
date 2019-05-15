package com.szy.myapplication.Retrofit;

import android.content.Context;


import com.szy.lib.network.Retrofit.ObserverApiCallback;
import com.szy.lib.network.Retrofit.Util.Dialog_util;
import com.szy.lib.network.Retrofit.Util.LogUtil;
import com.szy.myapplication.Utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava2.HttpException;


/**
 * Created by bingju on 2017/2/16.
 * 用来拦截是否重新登录的CallBack
 */

public abstract class OnObserverRetrofitResetCallBack<BaseEntity> extends ObserverApiCallback<BaseEntity> {
    protected Context context;

    public OnObserverRetrofitResetCallBack setContext(Context context) {
        this.context = context;
        return this;
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            int code = httpException.code();
            String msg = httpException.getMessage();
            ResponseBody body = ((HttpException) e).response().errorBody();
            try {
                String message = body.string();
                JSONObject jsonObject = new JSONObject(message);
                msg=jsonObject.getString("message");
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            LogUtil.d("code=" + code);
            switch (code) {
                case 504:
                    break;
            }
            onFailure(msg);
            ToastUtils.show_toast("错误信息"+ msg);
        } else {
            onFailure(e.getMessage());
        }
        Dialog_util.close_NetworkRequests_diolog();
        onFinish();
    }

    @Override
    public void onSuccess(BaseEntity model) {

    }
}
