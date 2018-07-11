package com.szy.myapplication.UI.layout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.szy.myapplication.Base.BaseActivity;
import com.szy.myapplication.R;

/**
 * ConstraintLayout布局
 */
public class ConstraintLayoutActivity extends BaseActivity {

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_constraint_layout;
    }

    @Override
    protected void initViews() {
        super.initViews();
        setTextTitleName("ConstraintLayout");
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        setBackOnclickListner(mContext);
    }
}
