package com.szy.myapplication.Base;

import android.app.Application;

import com.szy.myapplication.Utils.ApplicationHelp;

/**
 * Created by Administrator on 2018/1/12 0012.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationHelp.init_ibraries(this);
//        Bugly.init(getApplicationContext(), "534332d50d", true);
    }
}
