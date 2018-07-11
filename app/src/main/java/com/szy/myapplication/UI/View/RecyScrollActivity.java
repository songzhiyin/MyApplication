package com.szy.myapplication.UI.View;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
 * recyview滑动监听
 */
public class RecyScrollActivity extends BaseActivity {
    private ItemMessageAdapter adapter;
    private TextView tv_title;
    private String[] urls = new String[]{"http://hbimg.b0.upaiyun.com/79feb630cc2d5bc06117f1ab6a993130842221766e64f-xlKRBG_fw658", "http://hbimg.b0.upaiyun.com/fb38748dc9cd276e7a8b22c486a275c8c1fd937454415-B6bNRr_fw658"
            , "http://p5.so.qhimgs1.com/t01bd88726cccc5f66b.jpg", "http://p1.so.qhimgs1.com/bdr/_240_/t01d7df8156075bd6e1.jpg"};
    private int scrodY = 0;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_recy_scroll;
    }

    @Override
    protected void initViews() {
        super.initViews();
        recyclerView = $(R.id.recylist_message_list);
        tv_title = $(R.id.tv_recy_scroll_title);
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
        recyclerView.addOnScrollListener(onScrollListener);
    }


    /**
     * recyview的滑动监听
     */
    protected RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);


        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            scrodY = dy + scrodY;
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                int first = linearLayoutManager.findFirstVisibleItemPosition();
                int last = linearLayoutManager.findLastVisibleItemPosition();
                int top = recyclerView.getChildAt(1).getHeight() * 5;
                if (first >= 5 && scrodY >= top) {
                    tv_title.setVisibility(View.VISIBLE);
                } else {
                    tv_title.setVisibility(View.GONE);
                }
                Log.i(TAG, "first: " + first + "  last:" + last + " dx:" + dx + " dy:" + dy + " top:" + top + " scrodY" + scrodY);
            }
        }
    };

    @Override
    protected void initdatas() {
        super.initdatas();
        List<MessageEntity> messageEntities = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            messageEntities.add(new MessageEntity("标题" + (i + 1), "明天休息", "2018-01-10", urls[i % 4]));
        }
        adapter.addBottonDatas(messageEntities);
    }
}



