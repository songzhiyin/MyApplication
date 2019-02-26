package com.szy.myapplication.Base;

import android.app.Application;
import android.content.Context;

import com.szy.myapplication.Utils.ApplicationHelp;

/**
 * Created by Administrator on 2018/1/12 0012.
 */

public class MyApplication extends Application {
    public MyApplication() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationHelp.init_ibraries(this);
    }
}
