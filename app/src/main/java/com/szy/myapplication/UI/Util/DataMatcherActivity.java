package com.szy.myapplication.UI.Util;

import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.szy.myapplication.Base.BaseActivity;
import com.szy.myapplication.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式
 */
public class DataMatcherActivity extends BaseActivity {
    private EditText edtMatcher, edtMessage;
    private TextView tvMessage, tvConfirm;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_data_matcher;
    }

    @Override
    protected void initViews() {
        super.initViews();
        setTextTitleName("正则表达式");
        setBackOnclickListner(mContext);
        edtMatcher = $(R.id.edtMatcher);
        edtMessage = $(R.id.edtMessage);
        tvMessage = $(R.id.tvMessage);
        tvConfirm = $(R.id.tvConfirm);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String patten = edtMatcher.getText().toString().trim();
                    String message = edtMessage.getText().toString().trim();
                    Matcher matcher = Pattern.compile(patten).matcher(message);
                    StringBuffer buffer = new StringBuffer();
                    buffer.append("表达式：" + patten);
                    buffer.append("\n");
                    buffer.append("检验数据：" + message);
                    buffer.append("\n");
                    buffer.append("校验结果：" + matcher.matches());
                    tvMessage.setText(buffer.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    tvMessage.setText(e.toString());
                }
            }
        });
    }
}
