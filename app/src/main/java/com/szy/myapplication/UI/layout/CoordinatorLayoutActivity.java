package com.szy.myapplication.UI.layout;

import android.content.Context;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import com.lib.szy.pullrefresh.ItemDecoration.MyDeviderDecoration;
import com.lib.szy.pullrefresh.PullreFresh.PullRecyclerView;
import com.szy.myapplication.Adapter.ItemMessageAdapter;
import com.szy.myapplication.Base.BaseActivity;
import com.szy.myapplication.Entity.MessageEntity;
import com.szy.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * CoordinatorLayout
 * 类似美团外卖的滑动效果
 */
public class CoordinatorLayoutActivity extends AppCompatActivity {
    private ItemMessageAdapter adapter;
    private String[] urls = new String[]{"http://hbimg.b0.upaiyun.com/79feb630cc2d5bc06117f1ab6a993130842221766e64f-xlKRBG_fw658", "http://hbimg.b0.upaiyun.com/fb38748dc9cd276e7a8b22c486a275c8c1fd937454415-B6bNRr_fw658"
            , "http://p5.so.qhimgs1.com/t01bd88726cccc5f66b.jpg", "http://p1.so.qhimgs1.com/bdr/_240_/t01d7df8156075bd6e1.jpg"};
    private Toolbar mToolbar;
    private Context mContext;
    private PullRecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout);
        mContext = this;
        initViews();
        initdatas();
    }


    protected void initViews() {
        recyclerView = findViewById(R.id.recylist_message_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new MyDeviderDecoration(mContext, R.color.main_line_color, 1));
        adapter = new ItemMessageAdapter(mContext);
        recyclerView.setAdapter(adapter);
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("唐嫣");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    protected void initdatas() {
        List<MessageEntity> messageEntities = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            messageEntities.add(new MessageEntity("标题" + (i + 1), "明天休息", "2018-01-10", urls[i % 4]));
        }
        adapter.addBottonDatas(messageEntities);
    }


}
