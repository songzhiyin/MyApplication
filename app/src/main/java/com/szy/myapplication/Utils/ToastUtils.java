package com.szy.myapplication.Utils;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/2/6.
 * Toast的工具类，使用该工具类最好先在APP的起始位置，对该工具类进行初始化
 */

public class ToastUtils {

    private static Context contexts;
    private static String texts;
    private static Toast toast;

    /**
     * 初始化ToastUtils
     *
     * @param context AppLication对象
     */
    public static void inittostutils(Application context) {
        contexts = context;
    }

    /**
     * 检验context是否为空
     *
     * @return
     */
    public static boolean isInit() {
        return contexts != null;
    }

    /**
     * 显示toast的方法
     *
     * @param context 上下文对象
     * @param text    提示的文本内容
     */
    public static void show_toast(Context context, CharSequence text) {
        try {
            if (toast == null) {
                toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                toast.show();
            } else {
                toast.setText(text);
                toast.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示toast的方法,调用该方法之前要先调用inittostutils方法对该工具类进行初始化
     *
     * @param text 提示的文本内容
     */
    public static void show_toast(CharSequence text) {
        texts = (String) text;
        try {
            if (contexts != null) {
                if (toast == null) {
                    toast = Toast.makeText(contexts, text, Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    toast.setText(text);
                    toast.show();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        try {
//
//            Timer timer = new Timer();
//            timer.schedule(new TimerTask() {
//
//                @Override
//                public void run() {
//                    //调主线程方法，否则可能会显示不出来。
//                    handler.sendEmptyMessage(0);
//                }
//
//            }, 500);
////            Toast.makeText(contexts, text, Toast.LENGTH_SHORT).show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private static Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            Toast.makeText(contexts, texts, Toast.LENGTH_SHORT).show();

        }

    };

    public static void showToastNotNet() {
        show_toast("网络异常请检查您的网络");
    }

    public static void showToastSNR(Context context) {
        show_toast("服务器繁忙，请稍后！");
    }

}
