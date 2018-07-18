package com.szy.myapplication.UI.layout;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.lib.szy.pullrefresh.ItemDecoration.MyDeviderDecoration;
import com.szy.myapplication.Adapter.ItemMessageAdapter;
import com.szy.myapplication.Base.BaseActivity;
import com.szy.myapplication.Entity.MessageEntity;
import com.szy.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 详细分析AppBarLayout的五种ScrollFlags
 */
public class AppBarLayoutActivity extends BaseActivity {
    private ItemMessageAdapter adapter;
    private String[] urls = new String[]{"http://hbimg.b0.upaiyun.com/79feb630cc2d5bc06117f1ab6a993130842221766e64f-xlKRBG_fw658", "http://hbimg.b0.upaiyun.com/fb38748dc9cd276e7a8b22c486a275c8c1fd937454415-B6bNRr_fw658"
            , "http://p5.so.qhimgs1.com/t01bd88726cccc5f66b.jpg", "http://p1.so.qhimgs1.com/bdr/_240_/t01d7df8156075bd6e1.jpg"};


    @Override
    protected int getContentViewResId() {
        return R.layout.activity_app_bar_layout;
    }

    @Override
    protected void initViews() {
        super.initViews();
        recyclerView = $(R.id.recylist_message_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new MyDeviderDecoration(mContext, R.color.main_line_color, 1));
        adapter = new ItemMessageAdapter(mContext);
        recyclerView.setAdapter(adapter);
        setTextTitleName("AppBarLayou的ScrollFlags");
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        setBackOnclickListner(mContext);
//        setOnRefreshListener(true);
    }

    @Override
    protected void initdatas() {
        super.initdatas();
        List<MessageEntity> messageEntities = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            messageEntities.add(new MessageEntity("标题" + (i + 1), "明天休息", "2018-01-10", urls[i % 4]));
        }
        adapter.addBottonDatas(messageEntities);
    }

    @Override
    protected void onRefreshData() {
        super.onRefreshData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onRefreshDataSucceed();
                adapter.getData().clear();
                initdatas();
            }
        }, 1000);
    }

    @Override
    protected void onLoadData() {
        super.onLoadData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (adapter.getItemCount() > 14) {
                    onLoadDataFinish();
                    isLoadDataFinishck = true;
                    return;
                }
                onLoadDataSucceed();
                adapter.addBottonData(new MessageEntity("标题" + (adapter.getItemCount() + 1), "这是自动加载新增的", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()), "https://repair.flyoil.cn/repairapi/upload/system/bx_login_bg_811.png"));
            }
        }, 600);
    }

}
