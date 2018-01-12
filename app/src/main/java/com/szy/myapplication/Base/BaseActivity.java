package com.szy.myapplication.Base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.szy.myapplication.R;
import com.szy.myapplication.Utils.SystemBarUtils;

/**
 * Created by Administrator on 2018/1/12 0012.
 */

public abstract class BaseActivity extends Activity {
    protected Activity mContext;
    protected int requestedOrientation = 1111;//屏幕横竖的值
    private String TAG;
    /**
     * 状态栏
     */
    protected View view_statusbar;

    @LayoutRes
    protected abstract int getContentViewResId();

    protected <T extends View> T $(@IdRes int id) {
        return (T) findViewById(id);
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);

        if (requestedOrientation == 1111) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制设置为竖屏
        } else {
            setRequestedOrientation(requestedOrientation);//强制设置为竖屏
        }
        if (getContentViewResId() != 0) {
            setContentView(getContentViewResId());
        }
        TAG = "szy" + getRunningActivityName();
        initDefaultViews();
        initViews();
        initEvents();
        initdatas();
    }

    /**
     * 获取当前界面activity的名字
     *
     * @return
     */
    private String getRunningActivityName() {
        String contextString = mContext.toString();
        return contextString.substring(contextString.lastIndexOf(".") + 1, contextString.indexOf("@"));
    }

    /**
     * 初始化状态栏
     */
    private void initDefaultViews() {
        //设置状态栏高度
        view_statusbar = findViewById(R.id.view_statusbar);
        if (view_statusbar != null) {
            LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    SystemBarUtils.getStatusBarHeight(this)
            );
            view_statusbar.setBackgroundResource(R.color.homeColor);
            view_statusbar.setLayoutParams(ll);
        }
    }


    /**
     * 初始化view
     */
    protected void initViews() {
    }

    /**
     * 设置监听事件
     */
    protected void initEvents() {

    }

    /**
     * 加载数据
     */
    protected void initdatas() {
    }


}
