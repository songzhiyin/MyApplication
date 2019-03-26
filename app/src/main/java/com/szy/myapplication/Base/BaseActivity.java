package com.szy.myapplication.Base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.lib.szy.pullrefresh.PullreFresh.PullRecyclerView;
import com.szy.myapplication.R;
import com.szy.myapplication.Retrofit.HttpModel;
import com.szy.myapplication.Utils.NetWorkUtils;
import com.szy.myapplication.Utils.SystemBarUtils;

/**
 * Created by Administrator on 2018/1/12 0012.
 */

public abstract class BaseActivity extends FragmentActivity {
    protected Activity mContext;
    protected int requestedOrientation = 1111;//屏幕横竖的值
    protected String TAG;
    //标题、右边按钮
    protected TextView tv_title, tv_right;
    //返回按钮、右边图片按钮
    private ImageView img_lift, img_right;
    protected PullRecyclerView recyclerView;
    protected View line_network;//网络异常的提示
    private boolean remove_network = false;//是否要移除网络提示框
    protected int pageNumber = 1;//当前页数，默认页数是1
    protected int pageSize = 10;//每页数据条数
    protected boolean isFirstLoadck = true;//是否是首次加载，默认是true
    protected boolean isLoadDataFinishck = false;//数据是否加载完毕，默认是false
    protected HttpModel httpModel;
    protected ImmersionBar mImmersionBar;//沉浸式控制器

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
    protected void initDefaultViews() {
        mImmersionBar = ImmersionBar.with(this)
                .keyboardEnable(true)//解决软键盘与底部输入框冲突问题
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarColor(R.color.homeColor);
        mImmersionBar.init();
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

    protected void setTextRightName(String titleName) {
        if (tv_right == null) {
            return;
        }
        tv_right.setVisibility(titleName != null && titleName.length() > 0 ? View.VISIBLE : View.GONE);
        tv_right.setText(titleName);
    }

    protected void setTextRightOnClickListener(View.OnClickListener onClickListener) {
        if (tv_right != null && onClickListener != null) {
            tv_right.setOnClickListener(onClickListener);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            backFinish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
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
     * 设置recyview的上下拉加载监
     *
     * @param mEnableAutoLoading 是否可以进行自动加载
     */
    protected void setOnRefreshListener(boolean mEnableAutoLoading) {
        if (recyclerView == null) {
            return;
        }
        recyclerView.setmEnableAutoLoading(mEnableAutoLoading);
        recyclerView.setPullRefreshEnable(true);
        recyclerView.setOnRefreshListener(new PullRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新数据
                if (NetWorkUtils.isNetworkAvailable(mContext) == 0) {
                    onRefreshDataNetworkError();
                } else {
                    onRefreshData();
                }

            }

            @Override
            public void onLoadMore() {
                //自动加载数据
                if (NetWorkUtils.isNetworkAvailable(mContext) == 0) {
                    onLoadDataNetworkError();
                } else {
                    if (isLoadDataFinishck) {//判断数据是否已经加载完毕
                        onLoadDataFinish();
                        return;
                    }
                    onLoadData();
                }
            }
        });
    }

    /**
     * 下拉刷新重新加载数据
     */
    protected void onRefreshData() {
        pageNumber = 1;
        isLoadDataFinishck = false;
        if (recyclerView != null) {
            //当用户重新加载数据时，先隐藏底部自动加载的布局
            recyclerView.setVisibleLoadingData();
        }
    }

    /**
     * 下拉刷新数据，数据加载成功
     */
    protected void onRefreshDataSucceed() {
        //结束刷新
        if (recyclerView == null) {
            return;
        }
        recyclerView.refreshDataSucced();
        hide_network_hint();
    }

    /**
     * 下拉刷新数据，数据加载失败
     */
    protected void onRefreshDataError() {
        if (recyclerView == null) {
            return;
        }
        recyclerView.refreshDataError();
        hide_network_hint();
    }


    /**
     * 下拉刷新，网络异常数据加载失败
     */
    protected void onRefreshDataNetworkError() {
        if (recyclerView != null) {
            recyclerView.refreshDataError();
            show_network_hint();
        }

    }

    /**
     * 自动加载数据的方法
     */
    protected void onLoadData() {

    }

    /**
     * 自动加载数据，数据加载成功
     */
    protected void onLoadDataSucceed() {
        if (recyclerView == null) {
            return;
        }
        recyclerView.autoLoadingSucceed();
    }

    /**
     * 自动加载数据成功，并且数据加载完毕
     */
    protected void onLoadDataFinish() {
        if (recyclerView == null) {
            return;
        }
        recyclerView.autoLoadingFinish();
    }

    /**
     * 自动加载数据，数据加载失败
     */
    protected void onLoadDataError() {
        if (recyclerView == null) {
            return;
        }
        recyclerView.autoLoadingError();
    }

    /**
     * 自动加载数据，网络异常
     */
    protected void onLoadDataNetworkError() {
        recyclerView.autoLoadingNetwork();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }, 2000);

    }

    /**
     * 隐藏网络异常的提示
     */
    private void hide_network_hint() {
        if (line_network != null) {
            if (remove_network) {
                recyclerView.removeHeaderView(line_network);
                recyclerView.notifyDataSetChanged();
                line_network = null;
            } else {
                line_network.setVisibility(View.GONE);
            }

        }
    }

    /**
     * 显示网络异常的提示
     */
    private void show_network_hint() {
        if (line_network != null) {
            line_network.setVisibility(View.VISIBLE);
        } else {
            remove_network = true;
            line_network = LayoutInflater.from(mContext).inflate(R.layout.include_network_hint_layout, null);
            line_network.setVisibility(View.VISIBLE);
            recyclerView.addHeaderView(line_network);
        }
        if (line_network != null) {
            line_network.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent;
                    if (android.os.Build.VERSION.SDK_INT > 10) {
                        intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                    } else {
                        intent = new Intent();
                        ComponentName component = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
                        intent.setComponent(component);
                        intent.setAction("android.intent.action.VIEW");
                    }
                    startActivity(intent);
                }
            });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
    }
}
