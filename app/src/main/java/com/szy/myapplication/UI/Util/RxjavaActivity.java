package com.szy.myapplication.UI.Util;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TimeUtils;
import android.view.View;
import android.widget.TextView;

import com.szy.myapplication.Base.BaseActivity;
import com.szy.myapplication.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
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

        final int a = 60;
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(a + 1)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return a - aLong;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe(new Consumer<Disposable>() {//监听刚开始的时候
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                     tv1.setEnabled(false);
                        tv1.setTextColor(getResources().getColor(R.color.red));
                    }
                }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {
                tv1.setText("倒计时" + aLong + "秒");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                tv1.setText("获取验证码");
                tv1.setTextColor(getResources().getColor(R.color.tv_black));
                tv1.setEnabled(true);
            }
        });
    }

    /**
     * rxjav变换的用法——map操作符
     */
    private void demo2() {
        Observable.just("这是是超级卡", "撒擦三次", "的擦撒擦擦").subscribeOn(Schedulers.io())//指定 subscribe()发生在io线程，即被观察者发生在子线程中
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                tv2.append(s);
                tv2.append("\n");
            }
        });
    }


}
