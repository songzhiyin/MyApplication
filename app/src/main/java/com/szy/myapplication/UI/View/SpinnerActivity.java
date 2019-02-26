package com.szy.myapplication.UI.View;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.GridView;
import android.widget.ListAdapter;

import com.szy.myapplication.Base.BaseActivity;
import com.szy.myapplication.R;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.NiceSpinnerAdapterWrapper;
import org.angmarch.views.NiceSpinnerBaseAdapter;
import org.angmarch.views.SpinnerTextFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 下拉框
 */
public class SpinnerActivity extends BaseActivity {

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_spinner;
    }

    @Override
    protected void initViews() {
        super.initViews();
        setBackOnclickListner(mContext);
        setTextTitleName("下拉框");
        NiceSpinner niceSpinner = (NiceSpinner) findViewById(R.id.nice_spinner);
        List<String> dataset = new ArrayList<>();
        dataset.add("北京");
        dataset.add("河北");
        dataset.add("田径");
        dataset.add("网络");
        niceSpinner.setGravity(Gravity.LEFT);
        niceSpinner.attachDataSource(dataset);

    }




}
