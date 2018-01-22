package com.szy.myapplication.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.szy.myapplication.Base.BaseActivity;
import com.szy.myapplication.R;

public class TextviewActivity extends BaseActivity {

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_textview;
    }

    @Override
    protected void initViews() {
        super.initViews();
        setBackOnclickListner(mContext);
        setTextTitleName("自定义字体");
    }
}
