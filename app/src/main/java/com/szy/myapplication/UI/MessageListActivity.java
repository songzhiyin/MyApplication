package com.szy.myapplication.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.lib.szy.pullrefresh.ItemDecoration.MyDeviderDecoration;
import com.lib.szy.pullrefresh.PullreFresh.PullRecyclerView;
import com.szy.myapplication.Adapter.ItemMessageAdapter;
import com.szy.myapplication.Base.BaseActivity;
import com.szy.myapplication.Entity.MessageEntity;
import com.szy.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息列表
 */
public class MessageListActivity extends BaseActivity {
    private ItemMessageAdapter adapter;
    private PullRecyclerView recyclerView;

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
    }

    @Override
    protected void initdatas() {
        super.initdatas();
        List<MessageEntity> messageEntities = new ArrayList<>();
        for (int i = 0; i <8; i++) {
            messageEntities.add(new MessageEntity("标题"+(i+1), "明天休息", "2018-01-10", 0));
        }
        adapter.addBottonDatas(messageEntities);
    }
}
