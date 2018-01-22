package com.szy.myapplication.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/1/22 0022.
 */

@SuppressLint("AppCompatCustomView")
public class TextviewType extends TextView {
    // fongUrl是自定义字体分类的名称
    private static String fongUrl = "STXINGKA.ttf";
    //Typeface是字体，这里我们创建一个对象
    private static Typeface tf;

    public TextviewType(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * 初始化字体
     *
     * @param context
     */
    private void init(Context context) {
        if (tf == null) {
            //给它设置你传入的自定义字体文件，再返回回来
            tf = Typeface.createFromAsset(context.getAssets(), fongUrl);
        }
        //设置字体样式
        if (tf != null) {
            setTypeface(tf);
        } else {
            Log.e("error", "------------------------------没有字体资源文件--------------------");
        }

    }
}
