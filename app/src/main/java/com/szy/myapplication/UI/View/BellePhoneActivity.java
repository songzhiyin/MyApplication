package com.szy.myapplication.UI.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.lib.szy.pullrefresh.ItemDecoration.MyDeviderDecoration;
import com.szy.lib.network.Retrofit.ServiceGenerator;
import com.szy.myapplication.Adapter.ItemBellePhoneAdapter;
import com.szy.myapplication.Adapter.ItemMessageAdapter;
import com.szy.myapplication.Base.BaseActivity;
import com.szy.myapplication.Entity.BellePhoneEntity;
import com.szy.myapplication.R;
import com.szy.myapplication.Retrofit.HttpModel;
import com.szy.myapplication.Retrofit.OnObserverRetrofitResetCallBack;

public class BellePhoneActivity extends BaseActivity {
    private ItemBellePhoneAdapter adapter;

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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new MyDeviderDecoration(mContext, R.color.main_line_color, 1));
        adapter = new ItemBellePhoneAdapter(mContext);
        recyclerView.setAdapter(adapter);
        setTextTitleName("消息列表");
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        setOnRefreshListener(true);
    }

    @Override
    protected void initdatas() {
        super.initdatas();
        getBellePhone();
    }

    @Override
    protected void onRefreshData() {
        super.onRefreshData();
        getBellePhone();
    }

    @Override
    protected void onLoadData() {
        super.onLoadData();
        getBellePhone();
    }

    private void getBellePhone() {
        httpModel.getBellePhones(pageNumber + "", new OnObserverRetrofitResetCallBack<BellePhoneEntity>() {
            @Override
            public void onFailure(String msg) {

            }

            @Override
            public void onSuccess(BellePhoneEntity model) {
                super.onSuccess(model);
                BellePhoneEntity entity = null;
                entity = (BellePhoneEntity) model;
                if (entity != null && entity.getResults() != null) {
                    if (pageNumber == 1) {
                        adapter.getData().clear();
                        onRefreshDataSucceed();
                    } else {
                        onLoadDataSucceed();
                    }
                    adapter.addBottonDatas(entity.getResults());
                    recyclerView.notifyDataSetChanged();
                    pageNumber++;
                }
            }
        });
    }
}
