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

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        super.initViews();
        tv_recyview = $(R.id.tv_main_recyview);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        tv_recyview.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_main_recyview:
                    startActivity(new Intent(mContext, MessageListActivity.class));
                    break;
            }
        }
    };
}
