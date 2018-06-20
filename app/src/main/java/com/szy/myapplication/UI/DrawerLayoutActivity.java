package com.szy.myapplication.UI;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.szy.myapplication.Base.BaseActivity;
import com.szy.myapplication.R;

/**
 * 侧滑菜单
 */
public class DrawerLayoutActivity extends BaseActivity {
    private DrawerLayout mDrawerLayout;
    private Button mButtonLift;
    private Button mButtonRight;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_drawer_layout;
    }

    @Override
    protected void initViews() {
        super.initViews();
        mDrawerLayout = $(R.id.drawer_main_layout);
        mButtonLift = $(R.id.btn_main_lift);
        mButtonRight = $(R.id.btn_main_right);
        setTextTitleName("侧滑菜单");
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        setBackOnclickListner(mContext);
        mButtonLift.setOnClickListener(onClickListener);
        mButtonRight.setOnClickListener(onClickListener);

        mDrawerLayout.setDrawerListener(simpleDrawerListener);

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btn_main_lift:
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                    break;
                case R.id.btn_main_right:
                    mDrawerLayout.openDrawer(Gravity.RIGHT);
                    break;
            }
        }
    };
    private DrawerLayout.SimpleDrawerListener simpleDrawerListener = new DrawerLayout.SimpleDrawerListener() {

        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            Log.i(TAG, "打开了侧滑菜单 ");
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
            Log.i(TAG, "关闭了侧滑菜单 ");
        }
    };
}
