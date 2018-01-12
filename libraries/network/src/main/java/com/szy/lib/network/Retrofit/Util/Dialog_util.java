package com.szy.lib.network.Retrofit.Util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.szy.lib.network.R;


/**
 * Created by songzhiiyn on 2017/2/17.
 */

public class Dialog_util {
    private static Dialog dialog;
    private static Handler handler = new Handler();
    private static Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            //要做的事情
            close_NetworkRequests_diolog();
        }
    };


    /**
     * 开启显示网络请求的动画
     */
    public static void start_NetworkRequests_diolog(final Context context) {
        if (handler != null && runnable != null) {
            handler.postDelayed(runnable, 20000);//每两秒执行一次runnable.
        }
        if (dialog != null) {
            dialog.dismiss();
        }
        LayoutInflater inflaterDl = LayoutInflater.from(context);
        LinearLayout layout = (LinearLayout) inflaterDl.inflate(R.layout.diolog_network_request_show, null);
        dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        dialog.getWindow().setContentView(layout);
        dialog.setCancelable(false);
        WindowManager.LayoutParams params =
                dialog.getWindow().getAttributes();
        params.width = SystemUtils.dip2px(100, context);
        params.height = SystemUtils.dip2px(100, context);
        dialog.getWindow().setAttributes(params);
        ImageView loading_image = (ImageView) layout.findViewById(R.id.img_dialog_neywork_request_show);
        loading_image.setImageResource(R.drawable.dialog_network_loading);
        AnimationDrawable animationDrawable = (AnimationDrawable) loading_image.getDrawable();
        animationDrawable.setOneShot(false);
        animationDrawable.start();


    }

    /**
     * 关闭显示网络请求的动画
     */
    public static void close_NetworkRequests_diolog() {
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);//关闭计时任务
        }
        if (dialog != null) {
            dialog.dismiss();
        }
    }




}
