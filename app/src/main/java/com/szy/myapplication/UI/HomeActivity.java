package com.szy.myapplication.UI;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.MutableContextWrapper;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaychan.library.BottomBarLayout;
import com.szy.lib.network.Glide.GlideHelper;
import com.szy.myapplication.Adapter.ItemHomeMenuAdapter;
import com.szy.myapplication.Base.BaseActivity;
import com.szy.myapplication.R;
import com.szy.myapplication.Utils.EmulatorDetectorUtil;
import com.szy.myapplication.Utils.SimulatoUtil;
import com.szy.myapplication.Utils.ToastUtils;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnBindViewListener;
import com.timmy.tdialog.listener.OnViewClickListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.github.xudaojie.qrcodelib.CaptureActivity;

public class HomeActivity extends BaseActivity {
    private GridView grid;
    private ItemHomeMenuAdapter adapter;
    private Banner banner;
    private TextView tv_code;
    private int REQUEST_QR_CODE = 2010;


    @Override
    protected int getContentViewResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        super.initViews();
        grid = $(R.id.grid_main_list);
        tv_code = $(R.id.tv_home_versioncode);
        adapter = new ItemHomeMenuAdapter(mContext);
        grid.setAdapter(adapter);
        banner = $(R.id.banner_main);
        tv_code.setText("版本号：" + getAppVersionName(mContext) + "");
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        grid.setOnItemClickListener(onItemClickListener);
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    startActivity(new Intent(mContext, MessageListActivity.class));
                    break;
                case 1:
                    startActivity(new Intent(mContext, NetWorkActivity.class));
                    break;
                case 2:
                    startActivity(new Intent(mContext, EventBusTestActivity.class));
                    break;
                case 3://贝塞尔曲线
                    startActivity(new Intent(mContext, BezierDemoActivity.class));
                    break;
                case 4://滑动悬浮框
                    startActivity(new Intent(mContext, ScrollViewActivity.class));
                    break;
                case 5://recyview滑动监听
                    startActivity(new Intent(mContext, RecyScrollActivity.class));
                    break;
                case 6://图表
                    startActivity(new Intent(mContext, MpAndroidChartActivity.class));
                    break;
                case 7://水波纹
                    startActivity(new Intent(mContext, WaterWaveActivity.class));
                    break;
                case 8://自定义字体
                    startActivity(new Intent(mContext, TextviewActivity.class));
                    break;
                case 9://文字验证码
                    startActivity(new Intent(mContext, TextVerifyCodeActivity.class));
                    break;
                case 10://检测模拟器
                    if (EmulatorDetectorUtil.isEmulator(mContext))
                        ToastUtils.show_toast("这是模拟器");
                    else
                        ToastUtils.show_toast("这是真机");
                    break;
                case 11://轻量级的底部导航栏
                    startActivity(new Intent(mContext, BottomBarLayoutActivity.class));
                    break;
                case 12:
                    //注意6.0的安卓权限：android.permission.CAMERA、android.permission.VIBRATE、android.permission.FLASHLIGHT、android.permission.READ_EXTERNAL_STORAGE
                    Intent i = new Intent(mContext, CaptureActivity.class);
                    startActivityForResult(i, REQUEST_QR_CODE);
                    break;
                case 13://TDialog
                    showTDialog();
                    break;
                case 14://多个列表上下左右滑动时，可以联动
                    startActivity(new Intent(mContext, ListScrollActivity.class));
                    break;
            }
        }
    };

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK
                && requestCode == REQUEST_QR_CODE
                && data != null) {
            String result = data.getStringExtra("result");
            ToastUtils.show_toast("扫描到的数据" + result);
        }
    }

    @Override
    protected void initdatas() {
        super.initdatas();
        List<String> data = new ArrayList<>();
        data.add("recyview");
        data.add("retrofit");
        data.add("EventBus");
        data.add("贝塞尔曲线");
        data.add("滑动悬浮");
        data.add("列表滑动");
        data.add("图表");
        data.add("水波纹");
        data.add("自定义字体");
        data.add("文字验证码");
        data.add("检测模拟器");
        data.add("底部导航栏");
        data.add("二维码扫描");
        data.add("TDialog");
        data.add("多列表上下左右联动滑动");
        adapter.setdate(data);
        setBanner();
    }

    /**
     * 设置轮播图
     */
    private void setBanner() {
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置轮播图样式
        banner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器的位置
        banner.setDelayTime(2000);//设置轮播时间
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                GlideHelper.showImage(context, (String) path, imageView, R.mipmap.banner);
            }
        });//设置自定义图片加载器
        List<String> imgs = new ArrayList<>();
        imgs.add("http://p5.so.qhimgs1.com/bdr/326__/t01e08a43e499ade7fb.jpg");
        imgs.add("http://p2.so.qhimgs1.com/bdr/326__/t011112fbd57bd46022.jpg");
        imgs.add("http://p1.so.qhimgs1.com/bdr/326__/t0136042208a9f5ecbd.jpg");
        imgs.add("http://p1.so.qhmsg.com/bdr/326__/t015b7ff82bdcd90756.jpg");
        banner.setImages(imgs);
        banner.start();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                ToastUtils.show_toast("点击了第" + position + "张图");
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        banner.startAutoPlay();
    }

    @Override
    protected void onPause() {
        super.onPause();
        banner.stopAutoPlay();
    }

    /**
     * 获取当前版本号
     */
    private String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo("com.szy.myapplication", 0);
            versionName = packageInfo.versionName;
            if (TextUtils.isEmpty(versionName)) {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 展示TDialog的实例
     */
    private void showTDialog() {
        new TDialog.Builder(getSupportFragmentManager())
                .setLayoutRes(R.layout.dialog_finish_app)//设置布局
                .setGravity(Gravity.CENTER)//设置弹框的位置
                .setCancelableOutside(true)//设置点击弹框以外的位置，弹框是否消失
                .setCancelable(true)//设置弹框是否可以消失
                .setOnDismissListener(new DialogInterface.OnDismissListener() { //弹窗隐藏时回调方法
                    @Override
                    public void onDismiss(DialogInterface dialog) {
//                        ToastUtils.show_toast("弹窗消失回调");
                    }
                })
                .setOnBindViewListener(new OnBindViewListener() {   //通过BindViewHolder拿到控件对象,进行修改
                    @Override
                    public void bindView(BindViewHolder bindViewHolder) {
                        bindViewHolder.setText(R.id.tv_dialog_title, "这是基于dialogFragment的弹框框架");
                        bindViewHolder.setText(R.id.tv_dialog_lift, "获取");
                        bindViewHolder.setText(R.id.tv_dialog_right, "关闭");
                    }
                })
                .addOnClickListener(R.id.tv_dialog_lift, R.id.tv_dialog_right)   //添加进行点击控件的id
                .setOnViewClickListener(new OnViewClickListener() {     //View控件点击事件回调
                    @Override
                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                        switch (view.getId()) {
                            case R.id.tv_dialog_lift:
                                TextView textView = viewHolder.getView(R.id.tv_dialog_title);
                                ToastUtils.show_toast("提示内容：" + textView.getText().toString());
                                break;
                            case R.id.tv_dialog_right:
                                ToastUtils.show_toast("关闭dialog");
                                tDialog.dismiss();
                                break;
                        }
                    }
                })
                .create()
                .show();
    }
}
