package com.szy.myapplication.UI.Util;

import android.os.Environment;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.szy.myapplication.Base.BaseActivity;
import com.szy.myapplication.R;
import com.szy.myapplication.Utils.SkinManager;

import java.io.File;

import skin.support.SkinCompatManager;

/**
 * 实现换肤功能
 */
public class SkinDermaActivity extends BaseActivity {

    private TextView tvViewStyle,tvViewStyle2,tvViewStyle3;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_skin_derma;
    }


    @Override
    protected void initViews() {
        super.initViews();
        tvViewStyle = $(R.id.tvViewStyle);
        tvViewStyle2 = $(R.id.tvViewStyle2);
        tvViewStyle3 = $(R.id.tvViewStyle3);
    }

    @Override
    protected void initdatas() {
        super.initdatas();
        SkinManager.getInstance().init(mContext);
        File file = new File(Environment.getExternalStorageDirectory(), "$MuMu共享文件夹/skin.apk");
        if (file.exists()) {
            SkinManager.getInstance().loadSkinResouce(file.getAbsolutePath());
        }
    }

    @Override
    protected void initEvents() {
        super.initEvents();
//        setBackOnclickListner(mContext);
        tvViewStyle.setOnClickListener(onClickListener);
        tvViewStyle2.setOnClickListener(onClickListener);
        tvViewStyle3.setOnClickListener(onClickListener);
    }
    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.tvViewStyle://默认模式
                    SkinCompatManager.getInstance().restoreDefaultTheme();
                    break;
                case R.id.tvViewStyle2://高亮模式
                    SkinCompatManager.getInstance().loadSkin("white",SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN);//后缀
                    break;
                case R.id.tvViewStyle3://黑暗模式
                    SkinCompatManager.getInstance().loadSkin("black",SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN);//后缀

                    break;
            }
        }
    };
}
