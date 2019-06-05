package com.szy.myapplication.UI.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;

import com.szy.myapplication.Adapter.ItemMessageAdapter;
import com.szy.myapplication.Base.BaseActivity;
import com.szy.myapplication.Entity.MessageEntity;
import com.szy.myapplication.R;
import com.szy.myapplication.View.NestedScrollingWebView;

import java.util.ArrayList;
import java.util.List;

/**
 * webview和recyclerview的滑动兼容
 */
public class WebSroRecycleActivity extends BaseActivity {
    private NestedScrollingWebView webView;
    private RecyclerView mRecycler;
    private ItemMessageAdapter adapter;
    private String[] urls = new String[]{"http://hbimg.b0.upaiyun.com/79feb630cc2d5bc06117f1ab6a993130842221766e64f-xlKRBG_fw658", "http://hbimg.b0.upaiyun.com/fb38748dc9cd276e7a8b22c486a275c8c1fd937454415-B6bNRr_fw658"
            , "http://p5.so.qhimgs1.com/t01bd88726cccc5f66b.jpg", "http://p1.so.qhimgs1.com/bdr/_240_/t01d7df8156075bd6e1.jpg"};

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_web_sro_recycle;
    }

    @Override
    protected void initViews() {
        super.initViews();
        webView=$(R.id.web_message);
        mRecycler=$(R.id.mRecycler);
        adapter=new ItemMessageAdapter(mContext);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        mRecycler.setLayoutManager(linearLayoutManager);
        mRecycler.setAdapter(adapter);
        initWebData();
    }
    private void initWebData(){
       webView.getSettings().setJavaScriptEnabled(true);
       webView.setWebViewClient(new WebViewClient());
       webView.setWebChromeClient(new WebChromeClient());
       webView.loadUrl("https://www.cnblogs.com/meishan/p/5903477.html");
//       webView.loadUrl("https://segmentfault.com/a/1190000019272870");
    }

    @Override
    protected void initdatas() {
        super.initdatas();
        List<MessageEntity> messageEntities = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            messageEntities.add(new MessageEntity("标题" + (i + 1), "明天休息", "2018-01-10", urls[i % 4]));
        }
        adapter.setDataList(messageEntities);
    }
}

