package com.szy.myapplication.UI;

import android.content.Context;
import android.content.Intent;
import android.content.MutableContextWrapper;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szy.lib.network.Glide.GlideHelper;
import com.szy.myapplication.Adapter.ItemHomeMenuAdapter;
import com.szy.myapplication.Base.BaseActivity;
import com.szy.myapplication.R;
import com.szy.myapplication.Utils.ToastUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity {
    private GridView grid;
    private ItemHomeMenuAdapter adapter;
    private Banner banner;


    @Override
    protected int getContentViewResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        super.initViews();
        grid = $(R.id.grid_main_list);
        adapter = new ItemHomeMenuAdapter(mContext);
        grid.setAdapter(adapter);
        banner = $(R.id.banner_main);
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
            }
        }
    };

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
}
