package com.szy.myapplication.Retrofit;


import com.szy.lib.network.Retrofit.ServiceGenerator;
import com.szy.lib.network.Retrofit.Util.Dialog_util;
import com.szy.lib.network.Retrofit.Util.RequestBody_Util;
import com.szy.myapplication.Utils.ApplicationHelp;
import com.szy.myapplication.Utils.ToastUtils;


import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

/**
 * Created by bingju on 2017/2/15.
 */

public class HttpModelBase {
    private static RetrofitApiSerivce virtualbxApiSerivce;
    protected static RetrofitApiSerivce retrofitApiSerivce = getApiService();

    protected static RetrofitApiSerivce getApiService() {
        if (virtualbxApiSerivce == null) {
            synchronized (HttpModelBase.class) {
                if (virtualbxApiSerivce == null) {
                    virtualbxApiSerivce = ServiceGenerator.createService(RetrofitApiSerivce.class);
                }
            }
        }
        return virtualbxApiSerivce;
    }


    /**
     * 将serivce服务进行重置，将域名进行更换
     */
    public static void removeVirtualService() {
        virtualbxApiSerivce = null;
        retrofitApiSerivce = getApiService();
    }


    protected Map<String, String> getMap() {
        Map<String, String> maps = new HashMap<>();
        return maps;
    }

    protected Map<String, Object> getMap(Map<String, Object> maps) {
        if (maps == null) {
            maps = new HashMap<>();
        }

        return maps;
    }

    protected Map<String, RequestBody> getMapRequestBody(Map<String, RequestBody> maps) {
        if (maps == null) {
            maps = new HashMap<>();
        }

        return maps;
    }

    protected void setModel(Observable observable, Observer subscriber) {
        if (ApplicationHelp.isConnect() == false) {
            ToastUtils.show_toast("网络异常");
            Dialog_util.close_NetworkRequests_diolog();
            return;
        }
        if (observable == null) {
            throw new IllegalArgumentException("observable can't is null");
        }
        if (subscriber == null) {
            throw new IllegalArgumentException("subscriber can't is null");
        }
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
