package com.szy.myapplication.UI.View;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.lib.szy.pullrefresh.ItemDecoration.MyDeviderDecoration;
import com.szy.myapplication.Adapter.ItemPhotoAdapter;
import com.szy.myapplication.Base.BaseActivity;
import com.szy.myapplication.R;
import com.szy.myapplication.Retrofit.HttpModel;
import com.szy.myapplication.Retrofit.OnObserverRetrofitResetCallBack;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class BellePhoneActivity extends BaseActivity {
    private ItemPhotoAdapter adapter;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_belle_phone;
    }

    @Override
    protected void initViews() {
        super.initViews();
//        ServiceGenerator.setBaseUrl("http://gank.io/api");
        httpModel = new HttpModel();
        recyclerView = $(R.id.recylist_message_list);
        StaggeredGridLayoutManager recyclerViewLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView.addItemDecoration(new MyDeviderDecoration(mContext, R.color.main_line_color, 1));
        adapter = new ItemPhotoAdapter(mContext);
        recyclerView.setAdapter(adapter);
        setTextTitleName("图片列表");
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        setOnRefreshListener(true, true);
    }

    @Override
    protected void initdatas() {
        super.initdatas();
        getBellePhone();
    }

    @Override
    protected void onRefreshData() {
        super.onRefreshData();
        onRefreshDataSucceed();
    }

    @Override
    protected void onLoadData() {
        super.onLoadData();
        onLoadDataFinish();
        if (adapter.getItemCount() > 25) {
            return;
        }
        adapter.addBottonData(adapter.getItemData(adapter.getItemCount() / 3));
        recyclerView.notifyDataSetChanged();

    }

    private void getBellePhone() {
        List<String> data = new ArrayList<>();
        data.add("https://ww1.sinaimg.cn/large/0065oQSqly1ftzsj15hgvj30sg15hkbw.jpg");
        data.add("https://ws1.sinaimg.cn/large/610dc034gy1fi678xgq1ij20pa0vlgo4.jpg");
        data.add("https://ws1.sinaimg.cn/large/610dc034ly1fibksd2mbmj20u011iacx.jpg");
        data.add("https://ws1.sinaimg.cn/large/610dc034ly1fid5poqfznj20u011imzm.jpg");
        data.add("https://ws1.sinaimg.cn/large/610dc034ly1fiednrydq8j20u011itfz.jpg");
        data.add("https://ws1.sinaimg.cn/large/610dc034ly1fiiiyfcjdoj20u00u0ju0.jpg");
        data.add("https://ws1.sinaimg.cn/large/610dc034ly1fik2q1k3noj20u00u07wh.jpg");
        data.add("https://ws1.sinaimg.cn/large/610dc034ly1fil82i7zsmj20u011hwja");
        data.add("https://ws1.sinaimg.cn/large/610dc034ly1fir1jbpod5j20ip0newh3.jpg");
        data.add("https://ws1.sinaimg.cn/large/610dc034ly1fis7dvesn6j20u00u0jt4.jpg");
        data.add("https://ws1.sinaimg.cn/large/610dc034ly1fitcjyruajj20u011h412.jpg");
        data.add("https://ww1.sinaimg.cn/large/0065oQSqgy1ft4kqrmb9bj30sg10fdzq.jpg");
        data.add("https://ww1.sinaimg.cn/large/0065oQSqly1ft5q7ys128j30sg10gnk5.jpg");
        data.add("http://ww1.sinaimg.cn/large/0073sXn7ly1ft82s05kpaj30j50pjq9v.jpg");
        data.add("https://ww1.sinaimg.cn/large/0065oQSqly1ftdtot8zd3j30ju0pt137.jpg");
        data.add("https://ww1.sinaimg.cn/large/0065oQSqly1ftf1snjrjuj30se10r1kx.jpg");
        data.add("https://ww1.sinaimg.cn/large/0065oQSqgy1ftrrvwjqikj30go0rtn2i.jpg");
        data.add("http://ww1.sinaimg.cn/large/0065oQSqgy1ftt7g8ntdyj30j60op7dq.jpg");
        data.add("https://ww1.sinaimg.cn/large/0065oQSqly1ftu6gl83ewj30k80tites.jpg");
        data.add("https://ww1.sinaimg.cn/large/0065oQSqgy1ftwcw4f4a5j30sg10j1g9.jpg");
        adapter.setDataList(data);
        recyclerView.notifyDataSetChanged();

    }
}
