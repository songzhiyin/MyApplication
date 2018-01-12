package com.szy.myapplication.UI;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

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
 * 消息列表,该界面主要是为了测试recyview的下拉刷新和自动加载
 */
public class MessageListActivity extends BaseActivity {
    private ItemMessageAdapter adapter;


    @Override
    protected int getContentViewResId() {
        return R.layout.activity_message_list;
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
        setTextTitleName("消息列表");
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        setBackOnclickListner(mContext);
        setOnRefreshListener(true);
    }

    @Override
    protected void initdatas() {
        super.initdatas();
        List<MessageEntity> messageEntities = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            messageEntities.add(new MessageEntity("标题" + (i + 1), "明天休息", "2018-01-10", 0));
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
                adapter.addBottonData(new MessageEntity("标题" + (adapter.getItemCount() + 1), "这是自动加载新增的", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date())));
            }
        }, 600);
    }
}
