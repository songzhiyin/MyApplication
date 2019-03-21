package com.szy.myapplication.UI.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.szy.myapplication.Base.BaseActivity;
import com.szy.myapplication.R;
import com.szy.myapplication.View.StatusView;

/**
 * 状态文字view
 */
public class StatusTextViewActivity extends BaseActivity {
    private StatusView status;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_status_text_view;
    }

    @Override
    protected void initViews() {
        super.initViews();
        setTextTitleName("状态文字控件");
        setBackOnclickListner(mContext);
        status = $(R.id.status);
        status.setMessage("紧急");
    }
}
