package com.szy.myapplication.UI.View;

import android.app.Dialog;
import android.graphics.Color;
import android.printservice.CustomPrinterIconCallback;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.contrarywind.adapter.WheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.szy.myapplication.Base.BaseActivity;
import com.szy.myapplication.R;
import com.szy.myapplication.Utils.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 选择器
 */
public class WheelPickerActivity extends BaseActivity {
    private WheelView wv_time1, wv_time2, wv_time3;
    final List<String> data = new ArrayList<>();

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_wheel_picker;
    }

    @Override
    protected void initViews() {
        super.initViews();
        setTextTitleName("选择器");
        wv_time1 = $(R.id.wv_time1);
        wv_time2 = $(R.id.wv_time2);
        wv_time3 = $(R.id.wv_time3);
        initWheelView();

    }

    @Override
    protected void initEvents() {
        super.initEvents();
        wv_time1.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
//                ToastUtils.show_toast(data.get(i));
            }
        });
    }


    private void initWheelView() {
        final List<String> data = new ArrayList<>();
        data.add("北京海淀区就是彩色");
        data.add("北京该数据插卡式");
        data.add("擦几十块参数");
        data.add("查收理财卡");
        data.add("持卡三轮车看");
        data.add("看了擦上课");
        data.add("开始从拉萨开发");
        data.add("soif是拉上开始考虑才是");
        data.add("是持卡三轮车");
        data.add("搜是拉上开车撒");
        wv_time1.setAdapter(new ArrWheelAdapter(data));
        wv_time2.setAdapter(new ArrWheelAdapter(data));
        wv_time3.setAdapter(new ArrWheelAdapter(data));
        wv_time1.setDividerColor(Color.RED);//设置分割线颜色
//        wv_time1.setTextColorCenter(Color.RED);//设置选中item字体颜色
//        wv_time1.setTextColorOut(Color.YELLOW);//设置未选中item字体颜色

    }




    private class ArrWheelAdapter<T> implements WheelAdapter {
        private List<T> datas = new ArrayList<>();

        public ArrWheelAdapter(List<T> data_new) {
            this.datas = data_new;
        }

        @Override
        public int getItemsCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int i) {
            return datas.get(i);
        }

        @Override
        public int indexOf(Object o) {
            return datas.indexOf(o);
        }
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }
}
