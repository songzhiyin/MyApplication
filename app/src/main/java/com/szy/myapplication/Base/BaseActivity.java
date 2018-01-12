package com.szy.myapplication.Base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.RequiresPermission;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    //标题、右边按钮
    protected TextView tv_title, tv_right;
    //返回按钮、右边图片按钮
    private ImageView img_lift, img_right;

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
        initTitleView();
        initViews();
        initEvents();
        initdatas();
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
     * 初始化标题布局
     */
    private void initTitleView() {
        tv_title = $(R.id.tv_title_name);
        tv_right = $(R.id.tv_title_right);
        img_lift = $(R.id.img_title_back);
        img_right = $(R.id.img_title_right);
    }

    /**
     * 设置标题
     */
    protected void setTextTitleName(String titleName) {
        if (tv_title == null) {
            return;
        }
        tv_title.setVisibility(titleName != null && titleName.length() > 0 ? View.VISIBLE : View.GONE);
        tv_title.setText(titleName);
    }

    /**
     * 设置返回点击事件
     *
     * @param activity
     */
    protected void setBackOnclickListner(final Activity activity) {
        if (img_lift == null) {
            return;
        }
        img_lift.setVisibility(View.VISIBLE);
        img_lift.setImageResource(R.mipmap.title_back);
        img_lift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backFinish();
            }
        });
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
     * 自定义返回键
     */
    protected void backFinish() {
        finish();
        overridePendingTransition(R.anim.tuimout, R.anim.tuimin);//设置退出界面时的动画效果
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.zoomin, R.anim.zoomout);//设置界面跳转时的动画效果
    }

    @Override
    public void startActivityForResult(@RequiresPermission Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.zoomin, R.anim.zoomout);//设置界面跳转时的动画效果
    }

}
