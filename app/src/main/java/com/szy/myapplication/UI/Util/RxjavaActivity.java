package com.szy.myapplication.UI.Util;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.szy.myapplication.Base.BaseActivity;
import com.szy.myapplication.R;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * rxjava测试demo界面
 */
public class RxjavaActivity extends BaseActivity {
    private TextView tv1, tv2, tv3;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_rxjava;
    }

    @Override
    protected void initViews() {
        super.initViews();
        tv1 = $(R.id.textView);
        tv2 = $(R.id.textView2);
        tv3 = $(R.id.textView3);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        tv1.setOnClickListener(onClickListener);
        tv2.setOnClickListener(onClickListener);
        tv3.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.textView:
                    demo1();
                    break;
                case R.id.textView2:
                    demo2();
                    break;
                case R.id.textView3:
                    break;
            }
        }
    };

    /**
     * 基本的rxjava的用法
     */
    private void demo1() {
        Observable.just("", "", "").subscribeOn(Schedulers.io())//指定 subscribe()发生在io线程，即被观察者发生在子线程中
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.i(TAG, "onNext: " + s);
            }
        });
    }

    /**
     * rxjav变换的用法——map操作符
     */
    private void demo2() {

    }


}
