package com.szy.myapplication.Utils;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;


import com.szy.lib.network.Glide.GlideHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by songzhiiyn on 2017/9/25.
 */

public class ApplicationHelp {
    private static Context context;
    private static Map<String, Handler> handlerMap = new HashMap<>();

    /**
     * 初始化三方库
     */
    public static void init_ibraries(Application application) {
        context = application;
        ToastUtils.inittostutils(application);//初始化提示工具类
        GlideHelper.initGlide(application);//初始化Glide图片加载工具
    }

    public static Handler getHandler(String type) {
        if (type == null) {
            type = "";
        }
        Handler handler = handlerMap.get(type);
        if (handler == null) {
            handler = new Handler();
        }
        return handler;
    }

    public static void addHandler(Handler handler, String type) {
        if (handler != null && type != null) {
            handlerMap.put(type, handler);
        }
    }

    public static void setHandler(String type) {
        if (type == null) {
            type = "";
        }
        Handler handler = handlerMap.get(type);
        if (handler != null) {
            handler.sendEmptyMessage(100);
        }
    }

    /**
     * 检验当前网络是否可用
     *
     * @return
     */
    public static boolean isConnect() {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                // 获取网络连接管理的对象
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    // 判断当前网络是否已经连接
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
// TODO: handle exception
            Log.v("error", e.toString());
        }
        return false;
    }
}
