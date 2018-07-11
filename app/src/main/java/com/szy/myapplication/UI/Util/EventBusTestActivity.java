package com.szy.myapplication.UI.Util;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.szy.myapplication.Base.BaseActivity;
import com.szy.myapplication.Entity.EventBusEntity;
import com.szy.myapplication.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 测试EventBus的测试类
 */
public class EventBusTestActivity extends BaseActivity {
    private TextView tv_bnt;
    private TextView tv_btn2, tv_btn3, tv_btn4, tv_btn5;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_event_bus_test;
    }

    @Override
    protected void initViews() {
        super.initViews();
        tv_bnt = $(R.id.tv_main_bnt);
        tv_btn2 = $(R.id.tv_test1_button2);
        tv_btn3 = $(R.id.tv_test1_button3);
        tv_btn4 = $(R.id.tv_test1_button4);
        tv_btn5 = $(R.id.tv_test1_button5);
        setTextTitleName("EventBus");
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        //1、先在需要进行监听的界面，注册eventbus的事件
        EventBus.getDefault().register(this);
        tv_btn2.setOnClickListener(onClickListener);
        tv_btn3.setOnClickListener(onClickListener);
        tv_btn4.setOnClickListener(onClickListener);
        tv_btn5.setOnClickListener(onClickListener);
        setBackOnclickListner(mContext);
    }

    //2、构造接受消息的方法，该方法的修饰符必须是public
    @Subscribe(threadMode = ThreadMode.ASYNC)//在新的子线程中调用，用于执行耗时方法
    public void OnMessageEntityEvent(EventBusEntity entity) {
        if (entity == null) {
            Log.i("szy", "传递的信息: " + entity);
        }
    }

    //2、构造接受消息的方法，该方法的修饰符必须是public
    @Subscribe(threadMode = ThreadMode.MAIN)//在主线程中调用，用于刷新UI或者执行不耗时的事件
    public void OnMessageEntityEventUI(EventBusEntity entity) {
        if (entity != null) {
            if (entity.getName() != null) {
                tv_bnt.setText(entity.getName());
                Log.i("szy", "传递的信息: " + entity.getName());
            }
            if (entity.getNumber() > 0) {
                tv_bnt.setText("" + entity.getNumber());
                Log.i("szy", "传递的信息: " + entity.getNumber());
            }
            if (entity.getMoney() > 0) {
                tv_bnt.setText("" + entity.getMoney());
                Log.i("szy", "传递的信息: " + entity.getMoney());
            }
            if (entity.isHideck()) {
                tv_bnt.setText("" + entity.isHideck());
                Log.i("szy", "传递的信息: " + entity.isHideck());
            }

        } else {
            Log.i("szy", "传递的信息: " + entity);
        }

    }

    //3、发送消息
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_test1_button2:
                    EventBus.getDefault().post(new EventBusEntity("你好啊"));
                    break;
                case R.id.tv_test1_button3:
                    EventBus.getDefault().post(new EventBusEntity(true));
                    break;
                case R.id.tv_test1_button4:
                    EventBus.getDefault().post(new EventBusEntity(58));
                    break;
                case R.id.tv_test1_button5:
                    EventBus.getDefault().post(new EventBusEntity(12.12f));
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //4、取消注册，销毁监听事件
        EventBus.getDefault().unregister(this);

    }
}
