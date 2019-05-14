package com.szy.lib.network.Retrofit;



import com.szy.lib.network.Retrofit.Util.LogUtil;


import retrofit2.HttpException;
import rx.Subscriber;


/**
 * Created by bingju on 2017/1/4.
 * Rxjava 的一个Oberver的实现,用来接收并处理ApiService回调结果
 */

public abstract class ObserverApiCallback<M> extends Subscriber<M> {
    public abstract void onSuccess(M model);

    public abstract void onFailure(String msg);

    public void onFinish() {

    }
    protected void onlogin() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            int code = httpException.code();
            String msg = httpException.getMessage();
            LogUtil.d("code=" + code);
            switch (code) {
                case 504:
                    break;
            }
        } else {
            onFailure(e.getMessage());
        }
        onFinish();
    }

    public interface Request_code {
        public void show_code();
    }

    @Override
    public void onNext(M m) {
        if (m == null)
            onFailure("解析错误!");
        else
            onSuccess(m);
    }

    public void onCompleted() {
        onFinish();
    }
}
