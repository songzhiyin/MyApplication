package com.szy.myapplication.View;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szy.myapplication.R;

import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * Created by Administrator on 2018/1/24 0024.
 * 自定义
 */

public class VerifyCodeLayout extends ViewGroup {
    private int mWidth, mHeight;
    // 子View的水平间隔
    private final static int padding = 20;
    private Context mContext;
    private StringBuffer message = new StringBuffer();


    public VerifyCodeLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setChildViews();
    }

    /**
     * 重置文字内容
     */
    public void setChildViews() {
        if (message != null) {
            removeAllViews();
            requereMessage();
            for (int i = 0; i < message.length(); i++) {
                TextView textView = new TextView(mContext);
                textView.setTextColor(mContext.getResources().getColor(R.color.button_backgroud_red_click));
                textView.setTextSize(18);
                textView.setText(message.substring(i, i + 1));
                addView(textView);
            }
            invalidate();
            requestLayout();
        }
    }

    private void requereMessage() {
        message.delete(0, message.length());
        for (int i = 0; i < 5; i++) {
            message.append(getRandomChineseChar());
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        //如果布局里面设置的是固定值,这里取布局里面的固定值;如果设置的是match_parent,则取父布局的大小
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            //如果布局里面没有设置固定值,这里取布局的宽度的1/2
            width = widthSize * 1 / 2;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            //如果布局里面没有设置固定值,这里取布局的高度的3/4
            height = heightSize * 1 / 2;
        }
        mWidth = width - (getPaddingBottom() + getPaddingTop());
        mHeight = height - (getPaddingRight() + getPaddingLeft());
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 动态获取子View实例
        Random random = new Random();
        for (int i = 0, size = getChildCount(); i < size; i++) {

            int a = random.nextInt(80) + 20;
            int h = random.nextInt(mHeight  - 100) + 20;
            View view = getChildAt(i);
            // 放置子View，宽高都是100
            view.layout(l + a, t + h, l + a + 100, t + 100 + h);
            l += 100 + a + padding;
        }

    }

    /**
     * 随机生成一个汉字
     *
     * @return
     */
    private String getRandomChineseChar() {
        String str = null;
        int hs, ls;
        Random random = new Random();
        hs = (176 + Math.abs(random.nextInt(39)));
        ls = (161 + Math.abs(random.nextInt(93)));
        byte[] b = new byte[2];
        b[0] = (new Integer(hs).byteValue());
        b[1] = (new Integer(ls).byteValue());
        try {
            str = new String(b, "GBk"); //转成中文
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return str;
    }
}