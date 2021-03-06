package com.szy.myapplication.UI.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.szy.myapplication.Base.BaseActivity;
import com.szy.myapplication.R;
import com.szy.myapplication.View.ImageVFCodeView;
import com.szy.myapplication.View.VerifyCodeLayout;

import java.io.UnsupportedEncodingException;
import java.util.Random;

public class TextVerifyCodeActivity extends BaseActivity {
    private VerifyCodeLayout verifyCodeLayout;
    private TextView tv_updata, tv_updataimg;
    private ImageVFCodeView imageVFCodeView;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_text_verify_code;
    }

    @Override
    protected void initViews() {
        super.initViews();
        setTextTitleName("验证码");
        verifyCodeLayout = $(R.id.verfity_code);
        tv_updata = $(R.id.tv_verfity_code_updata);
        tv_updataimg = $(R.id.tv_verfity_code_updata_img);
        imageVFCodeView = $(R.id.imavfc_verfity_code);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        setBackOnclickListner(mContext);
        tv_updata.setOnClickListener(onClickListener);
        tv_updataimg.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_verfity_code_updata:
                    verifyCodeLayout.setChildViews();
                    break;
                case R.id.tv_verfity_code_updata_img:
                    imageVFCodeView.setMessage();
                    break;
            }
        }
    };


}
