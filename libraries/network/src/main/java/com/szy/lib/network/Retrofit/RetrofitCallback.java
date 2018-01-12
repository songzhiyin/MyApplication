package com.szy.lib.network.Retrofit;



import com.szy.lib.network.Retrofit.Util.LogUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bingju on 2017/1/4.
 */

public abstract class RetrofitCallback<M> implements Callback<M> {
    public abstract void onSuccess(M model);

    public abstract void onFailure(String msg);

    public abstract void onThrowable(Throwable t);

    public abstract void onFinish();
    @Override
    public void onResponse(Call<M> call, Response<M> response) {
        if (response.isSuccessful()) {
            if (response.body() == null)
                onFailure("解析错误!");
            else
                onSuccess(response.body());
        } else {
            int code = response.code();
            String msg = response.errorBody().toString();
            LogUtil.d("code=" + code);
            switch (code) {
                case 504:
                    msg = "网络不给力";
                    break;
                case 502:
                case 404:
                    msg = "服务器异常，请稍后再试";
                    break;
            }
            onFailure(msg);
        }
        onFinish();
    }

    @Override
    public void onFailure(Call<M> call, Throwable t) {
        onThrowable(t);
        onFinish();
    }
}
