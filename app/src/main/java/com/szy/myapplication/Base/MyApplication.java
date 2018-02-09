package com.szy.myapplication.Base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.szy.myapplication.Utils.ApplicationHelp;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by Administrator on 2018/1/12 0012.
 */

public class MyApplication extends TinkerApplication {
    public MyApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.szy.myapplication.Utils.SampleApplicationLike",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }

}
