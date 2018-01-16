package com.szy.myapplication.UI;

import android.content.Intent;
import android.content.MutableContextWrapper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.szy.myapplication.Base.BaseActivity;
import com.szy.myapplication.R;

public class MainActivity extends BaseActivity {
    private TextView tv_recyview;
    private TextView tv_retrofit;
    private TextView tv_EventBus;
    private TextView tv_bezier;//贝塞尔曲线
    private TextView tv_ScrollView;//滑动悬浮框
    private TextView tv_recy_scro;//滑动悬浮框
    private TextView tv_chart;//图表

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        super.initViews();
        tv_recyview = $(R.id.tv_main_recyview);
        tv_retrofit = $(R.id.tv_main_retrofit);
        tv_EventBus = $(R.id.tv_main_EventBus);
        tv_bezier = $(R.id.tv_main_bezier);
        tv_ScrollView = $(R.id.tv_main_ScrollView);
        tv_recy_scro = $(R.id.tv_main_recyview_scroll);
        tv_chart = $(R.id.tv_main_mp_android_chart);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        tv_recyview.setOnClickListener(onClickListener);
        tv_retrofit.setOnClickListener(onClickListener);
        tv_EventBus.setOnClickListener(onClickListener);
        tv_bezier.setOnClickListener(onClickListener);
        tv_ScrollView.setOnClickListener(onClickListener);
        tv_recy_scro.setOnClickListener(onClickListener);
        tv_chart.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_main_recyview:
                    startActivity(new Intent(mContext, MessageListActivity.class));
                    break;
                case R.id.tv_main_retrofit:
                    startActivity(new Intent(mContext, NetWorkActivity.class));
                    break;
                case R.id.tv_main_EventBus:
                    startActivity(new Intent(mContext, EventBusTestActivity.class));
                    break;
                case R.id.tv_main_bezier://贝塞尔曲线
                    startActivity(new Intent(mContext, BezierDemoActivity.class));
                    break;
                case R.id.tv_main_ScrollView://滑动悬浮框
                    startActivity(new Intent(mContext, ScrollViewActivity.class));
                    break;
                case R.id.tv_main_recyview_scroll://recyview滑动监听
                    startActivity(new Intent(mContext, RecyScrollActivity.class));
                    break;
                case R.id.tv_main_mp_android_chart://图表
                    startActivity(new Intent(mContext, MpAndroidChartActivity.class));
                    break;
            }
        }
    };
}
