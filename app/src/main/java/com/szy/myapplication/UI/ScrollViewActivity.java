package com.szy.myapplication.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.szy.myapplication.Base.BaseActivity;
import com.szy.myapplication.R;

/**
 * Scrollview滑动到某一个位置时，布局悬浮在界面上
 */
public class ScrollViewActivity extends BaseActivity {
    private TextView tv_btn, tv_btn_gone;
    private ScrollView scrollView;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_scroll_view;
    }

    @Override
    protected void initViews() {
        super.initViews();
        setTextTitleName("悬浮框");
        setBackOnclickListner(mContext);
        tv_btn = $(R.id.tv_scroll_btn);
        tv_btn_gone = $(R.id.tv_scroll_btn_gone);
        scrollView = $(R.id.scrview_scroll);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        scrollView.setOnScrollChangeListener(onScrollChangeListener);
    }

    private View.OnScrollChangeListener onScrollChangeListener = new View.OnScrollChangeListener() {
        @Override
        public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            Log.i(TAG, "onScrollChange: scrollY" + scrollY + "  scrollX:" + scrollX + " oldScrollX:" + oldScrollX + " oldScrollY:" + oldScrollY);
            if (scrollY > tv_btn.getTop()) {
                tv_btn_gone.setVisibility(View.VISIBLE);
            } else {
                tv_btn_gone.setVisibility(View.GONE);
            }
        }
    };
}
