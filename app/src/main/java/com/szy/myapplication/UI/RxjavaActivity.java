package com.szy.myapplication.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.szy.myapplication.Base.BaseActivity;
import com.szy.myapplication.R;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


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
        //创建Observable——被观察者
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {

                subscriber.onNext("hello");//发起事件
                subscriber.onNext("world");
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())//指定 subscribe()发生在io线程，即被观察者发生在子线程中
                .observeOn(AndroidSchedulers.mainThread())//指定 Subscriber 的回调发生在主线程，即观察者发生在主线程
                .subscribe(new Subscriber<String>() {//创建Subscriber——观察者
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "结束任务");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, "onNext: " + s);
                    }
                });
    }

    /**
     *rxjav变换的用法——map操作符
     */
    private void demo2() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("你好啊");
                subscriber.onNext("一点也不好");
                subscriber.onCompleted();
            }
        }).map(new Func1<String, Boolean>() {//执行map操作符，进行事件变换——将string事件转化为Boolean事件
            @Override
            public Boolean call(String s) {
                Log.i(TAG, "call: " + s);
                if ("一点也不好".equals(s)) {
                    return false;
                }
                return true;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "结束任务");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        Log.i(TAG, "传递的布尔值" + aBoolean);
                    }
                });
    }


}
