package com.szy.myapplication.UI.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.szy.myapplication.Base.BaseActivity;
import com.szy.myapplication.R;
import com.szy.myapplication.View.HeartView;

public class LoveViewActivity extends BaseActivity {
private HeartView heart;
    @Override
    protected int getContentViewResId() {
        return R.layout.activity_love_view;
    }

    @Override
    protected void initViews() {
        super.initViews();
        heart=$(R.id.heart);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        setBackOnclickListner(mContext);
        heart.setOnClickListener(onClickListener);

    }

    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            heart.start();
        }
    };
}
