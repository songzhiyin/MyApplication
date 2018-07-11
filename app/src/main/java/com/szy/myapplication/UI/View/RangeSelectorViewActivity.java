package com.szy.myapplication.UI.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.szy.myapplication.Base.BaseActivity;
import com.szy.myapplication.R;
import com.szy.myapplication.View.RangeSelectorView;

/**
 * 范围选择器
 */
public class RangeSelectorViewActivity extends BaseActivity {
    private RangeSelectorView ragse;
    private EditText edt_amoney, edt_lift, edt_right;
    private TextView tv_confirm;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_range_selector_view;
    }

    @Override
    protected void initViews() {
        super.initViews();
        setTextTitleName("范围选择器");
        ragse = $(R.id.ragse);
        tv_confirm = $(R.id.tv_confirm);
        edt_amoney = $(R.id.edt_amoney);
        edt_lift = $(R.id.edt_lift);
        edt_right = $(R.id.edt_right);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        setBackOnclickListner(mContext);
        tv_confirm.setOnClickListener(onClickListener);
        ragse.setOnXyTouchListener(onXyTouchListener);
    }

    private RangeSelectorView.OnXyTouchListener onXyTouchListener = new RangeSelectorView.OnXyTouchListener() {
        @Override
        public void onLiftNumber(int number) {
            edt_lift.setText(number + "");
        }

        @Override
        public void onRightNumber(int number) {
            edt_right.setText(number + "");
        }
    };

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_confirm:
                    ragse.setAllNumber(Integer.parseInt(edt_amoney.getText().toString()));
                    ragse.setLiftNumer(Integer.parseInt(edt_lift.getText().toString()));
                    ragse.setRightNumber(Integer.parseInt(edt_right.getText().toString()));
                    break;
            }
        }
    };

}
