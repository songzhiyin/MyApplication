package com.szy.myapplication.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.szy.myapplication.Base.BaseActivity;
import com.szy.myapplication.R;

/**
 * 绘制view和缩小动画
 */
public class CancasBitmapActivity extends BaseActivity {

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_cancas_bitmap;
    }

    @Override
    protected void initViews() {
        super.initViews();
        setTextTitleName("绘制图片");
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        setBackOnclickListner(mContext);
    }
}
